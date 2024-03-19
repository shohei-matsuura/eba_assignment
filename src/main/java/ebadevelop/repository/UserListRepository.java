package ebadevelop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebadevelop.entity.UserList;

/**
 * user_listテーブルを操作するためのRepositoryクラス
 */
public interface UserListRepository extends JpaRepository<UserList, Integer> {
    // 修正：findAllByIdメソッドの戻り値をList<User>に変更
    List<UserList> findAllById(Iterable<Integer> ids);

    // 修正：パラメータの型を一致させるために、整数型から文字列型に変更
    UserList findByIdAndPassword(Integer id, String password);
    
	@Query(value="SELECT * FROM user_list WHERE id != :id", nativeQuery=true)
	public List<UserList> findAllExcept(@Param("id") Integer id);
    
	@Query(value="SELECT icon FROM user_list WHERE id = :id", nativeQuery=true)
	public byte[] findIconById(@Param("id") Integer id);

}
