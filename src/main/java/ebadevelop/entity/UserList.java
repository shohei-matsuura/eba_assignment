package ebadevelop.entity;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * user_listテーブルのEntityクラス
 */
@Entity
@Table(name="user_list")
public class UserList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private Integer age;
	@Column(name = "password")
	private String password; // パスワードフィールドのデータ型をStringに変更
	@NotNull
	@Column(name="icon")
	private byte[] icon;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return age
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * @param age セットする age
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return icon
	 */
	public byte[] geticon() {
		return icon;
	}
	/**
	 * @param password セットする password
	 */
	public void seticon(byte[] icon) {
		this.icon = icon;
	}
	
}