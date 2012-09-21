package jp.mwsoft.sample.mahout.id;

import org.apache.mahout.cf.taste.impl.model.MySQLJDBCIDMigrator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MySQLJDBCIDMigratorSample {

	public static void main(String[] args) throws Exception {

		// DataSourceの用意
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName("localhost");
		ds.setUser("root");
		ds.setPassword("tiger");
		ds.setDatabaseName("mahout");

		// migratorの用意
		MySQLJDBCIDMigrator migrator = new MySQLJDBCIDMigrator(ds,
				"table_name", "id_column", "value_column");

		// とりあえずsotreしてみる
		migrator.storeMapping(migrator.toLongID("田中"), "田中");
		migrator.storeMapping(migrator.toLongID("佐藤"), "佐藤");
		migrator.storeMapping(migrator.toLongID("鈴木"), "鈴木");

		// IDから名前を取ってみる
		String name = migrator.toStringID(migrator.toLongID("田中"));
		System.out.println(name);
	}
}
