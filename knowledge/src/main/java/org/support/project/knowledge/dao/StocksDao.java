package org.support.project.knowledge.dao;

import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.gen.GenStocksDao;
import org.support.project.knowledge.entity.StocksEntity;
import org.support.project.knowledge.vo.Stock;
import org.support.project.ormapping.common.SQLManager;
import org.support.project.web.bean.LoginedUser;

/**
 * ストックしたナレッジ
 */
@DI(instance=Instance.Singleton)
public class StocksDao extends GenStocksDao {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;
	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static StocksDao get() {
		return Container.getComp(StocksDao.class);
	}
	
	/**
	 * 自分が登録したストックを取得
	 * @param loginedUser
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<StocksEntity> selectMyStocksWithKnowledgeCount(LoginedUser loginedUser, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StocksDao/StocksDao_selectMyStocksWithKnowledgeCount.sql");
		return executeQueryList(sql, StocksEntity.class, loginedUser.getUserId(), limit, offset);
	}

	/**
	 * 自分が登録したストックを取得
	 * （ナレッジIDを指定し、各ストックにそのナレッジがストックされているかも同時に取得）
	 * @param loginedUser
	 * @param knowledgeId
	 * @param i
	 * @param listLimit
	 * @return
	 */
	public List<Stock> selectMyStocksWithStocked(LoginedUser loginedUser, Long knowledgeId, int offset, int limit) {
		String sql = SQLManager.getInstance().getSql("/org/support/project/knowledge/dao/sql/StocksDao/StocksDao_selectMyStocksWithStocked.sql");
		return executeQueryList(sql, Stock.class, knowledgeId, loginedUser.getUserId(), limit, offset);
	}



}
