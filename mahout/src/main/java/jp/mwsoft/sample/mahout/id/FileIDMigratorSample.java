package jp.mwsoft.sample.mahout.id;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileIDMigrator;

public class FileIDMigratorSample {

	public static void main(String[] args) throws IOException, TasteException {

		FileIDMigrator migrator = new FileIDMigrator(new File("data/ids.txt"));

		long id = migrator.toLongID("田中");
		System.out.println(id);

		String name = migrator.toStringID(id);
		System.out.println(name);
	}
}
