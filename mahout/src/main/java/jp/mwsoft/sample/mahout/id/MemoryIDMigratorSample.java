package jp.mwsoft.sample.mahout.id;

import java.util.Arrays;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;

public class MemoryIDMigratorSample {

	public static void main(String[] args) {

		List<String> list = Arrays.asList(
				"田中", "佐藤", "鈴木", "高橋", "伊藤",
				"山本", "渡辺", "中村", "小林", "加藤");

		MemoryIDMigrator migrator = new MemoryIDMigrator();
		migrator.initialize(list);

		// 文字列 → ID
		long id = migrator.toLongID("田中");
		System.out.println(id);

		// ID → 文字列
		String name = migrator.toStringID(id);
		System.out.println(name);
		
		System.out.println(migrator.toLongID("藤原"));
		
		System.out.println(migrator.toStringID(-3962337966239618561L));
		migrator.storeMapping(-3962337966239618561L, "坂本");
		System.out.println(migrator.toStringID(-3962337966239618561L));

		// 追加する
		migrator.storeMapping(migrator.toLongID("横山"), "横山");
		id = migrator.toLongID("横山");
		System.out.println(id);
	}
}
