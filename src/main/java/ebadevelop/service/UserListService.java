package ebadevelop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebadevelop.entity.UserList;
import ebadevelop.repository.UserListRepository;

/**
 * user_list関連のServiceを提供するクラス
 */
@Service
public class UserListService {

	@Autowired
	UserListRepository UserListRepository;

	/**
	 * user_listテーブル内のレコードを全件検索して返却
	 * @return	user_listテーブル内の全情報
	 */
	public List<UserList> findAll() {
		return UserListRepository.findAll();
	}
}