/**
* TODO
* @Project: esframe
* @Title: IndexFiles.java
* @Package com.appframe.component.lucene
* @author jason.liu
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * TODO
 * 
 * @ClassName: IndexFiles
 * @author jason.liu
 */
public class IndexFiles {

	/**
	 * TODO
	 * 
	 * @Title: main
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String indexPath = "E:\\indexdata";//索引存放目录
		System.out.println("Indexing to directory '" + indexPath + "' ...");

		String docsPath = "D:\\docs\\ISO-QP-08软件开发控制程序";//原始数据目录
		final Path docDir = Paths.get(docsPath);

		Date start = new Date();
		try {
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			boolean create = true;
			if (create) {
				// Create a new index in the directory, removing any previously
				// indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}

			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs(writer, docDir);

			writer.close();
			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}

	}

	/**
	 * TODO Indexes the given file using the given writer, or if a directory is
	 * given, recurses over files and directories found under the given
	 * directory.
	 * 
	 * @Title: indexDocs
	 * @param writer
	 *            Writer to the index where the given file/dir info will be
	 *            stored
	 * @param path
	 *            The file to index, or the directory to recurse into to find
	 *            files to index
	 * @throws IOException
	 */
	static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		if (Files.isDirectory(path)) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					try {
						indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
					} catch (IOException ignore) {
						// don't index files that can't be read.
					}
					return FileVisitResult.CONTINUE;
				}

			});
		} else {
			indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
		}
	}

	/**
	 * TODO Index a single document
	 * 
	 * @Title: indeDoc
	 * @param writer
	 * @param file
	 * @param lastModified
	 * @throws IOException
	 */
	static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
		InputStream stream = Files.newInputStream(file);
		Document doc = new Document();

		// Add the path of the file as a field named "path". Use a
		// field that is indexed (i.e. searchable), but don't tokenize
		// the field into separate words and don't index term frequency
		// or positional information:
		Field pathField = new StringField("path", file.toString(), Field.Store.YES);
		doc.add(pathField);
		// Add the last modified date of the file a field named "modified".
		// Use a LongPoint that is indexed (i.e. efficiently filterable with
		// PointRangeQuery). This indexes to milli-second resolution, which
		// is often too fine.
		doc.add(new LongPoint("modified", lastModified));
		// Add the contents of the file to a field named "contents". Specify
		// a Reader,
		// so that the text of the file is tokenized and indexed, but not
		// stored.
		// Note that FileReader expects the file to be in UTF-8 encoding.
		// If that's not the case searching for special characters will fail.
		doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

		if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
			// New index, so we just add the document (no old document can be
			// there):
			System.out.println("adding " + file);
			writer.addDocument(doc);
		} else {
			// Existing index (an old copy of this document may have been
			// indexed) so
			// we use updateDocument instead to replace the old one matching the
			// exact
			// path, if present:
			System.out.println("updating " + file);
			writer.updateDocument(new Term("path", file.toString()), doc);
		}
	}

}
