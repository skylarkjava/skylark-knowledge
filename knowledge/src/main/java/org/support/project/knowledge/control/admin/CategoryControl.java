package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSONException;

import org.apache.http.HttpStatus;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.dao.CategoriesDao;
import org.support.project.knowledge.entity.CategoriesEntity;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.CategoryLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class CategoryControl extends Control {
	/** ログ */
	private static Log LOG = LogFactory.getLog(CategoryControl.class);

	/**
	 * テンプレートの一覧を表示
	 * @return
	 */
	@Get
	@Auth(roles="admin")
	public Boundary list() {
		// テンプレートの個数はあまり多く出来ないようにする（でないと登録の画面が微妙）
		CategoriesDao categoryDao = CategoriesDao.get();
		List<CategoriesEntity> categories = categoryDao.selectAll();
		Collections.sort(categories, new Comparator<CategoriesEntity>() {
			@Override
			public int compare(CategoriesEntity o1, CategoriesEntity o2) {
				if (!o1.getCategoryId().equals(o2.getCategoryId())) {
					return o1.getCategoryId().compareTo(o2.getCategoryId());
				}
				return 0;
			}
		});
		setAttribute("categories", categories);
		return forward("list.jsp");
	}


	/**
	 * 登録画面を表示する
	 * @return
	 */
	@Get
	@Auth(roles="admin")
	public Boundary view_add() {
		return forward("view_add.jsp");
	}

	/**
	 * 更新画面を表示する
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	@Auth(roles="admin")
	public Boundary view_edit() throws InvalidParamException {
		Integer id = super.getPathInteger();
		CategoriesEntity entity = CategoryLogic.get().loadCategory(id);
		if (entity == null) {
			sendError(404, null);
		}
		setAttributeOnProperty(entity);

		boolean editable = true;

		setAttribute("editable", editable);
		return forward("view_edit.jsp");
	}


	/**
	 * リクエスト情報にあるテンプレートの情報を取得する
	 * 同時にバリデーションエラーもチェックし、もしバリデーションエラーが発生する場合、
	 * 引数のerrorsのリストに追加していく
	 *
	 * @param errors
	 * @return
	 * @throws InvalidParamException
	 * @throws IOException
	 * @throws JSONException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private CategoriesEntity loadParams(List<ValidateError> errors) throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		CategoriesEntity category = new CategoriesEntity();
		Map<String, String> values = getParams();
		errors.addAll(category.validate(values));
		if (!errors.isEmpty()) {
			return null;
		}
		category = getParamOnProperty(CategoriesEntity.class);

		return category;
	}


	/**
	 * 登録
	 * 画面遷移すると再度画面を作るのが面倒なので、Ajaxアクセスとする
	 * @return
	 * @throws InvalidParamException
	 * @throws IOException
	 * @throws JSONException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Post
	@Auth(roles="admin")
	public Boundary create() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		List<ValidateError> errors = new ArrayList<ValidateError>();
		CategoriesEntity category = loadParams(errors);
		if (!errors.isEmpty()) {
			return sendValidateError(errors);
		}
		category.setCategoryId(null); //自動採番する
		// 保存
		category = CategoryLogic.get().addCategory(category, getLoginedUser());

		// メッセージ送信
		return sendMsg(MessageStatus.Success, HttpStatus.SC_OK, String.valueOf(category.getCategoryId()), "message.success.insert");
	}



	/**
	 * 更新
	 * @return
	 * @throws InvalidParamException
	 * @throws IOException
	 * @throws JSONException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Post
	@Auth(roles="admin")
	public Boundary update() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		List<ValidateError> errors = new ArrayList<ValidateError>();
		CategoriesEntity category = loadParams(errors);
		if (!errors.isEmpty()) {
			return sendValidateError(errors);
		}

		// 保存
		try {
			category = CategoryLogic.get().updateCategory(category, getLoginedUser());
		} catch (InvalidParamException e) {
			// エラーメッセージ送信
			return send(e.getMessageResult());
		}
		// メッセージ送信
		return sendMsg(MessageStatus.Success, HttpStatus.SC_OK, String.valueOf(category.getCategoryId()), "message.success.update");
	}


	/**
	 * 削除
	 * @return
	 * @throws Exception
	 */
	@Post
	@Auth(roles="admin")
	public Boundary delete() throws Exception {
		Integer categoryId = getParam("categoryId", Integer.class);

		CategoryLogic.get().deleteCategory(categoryId, getLoginedUser());
		String successMsg = "message.success.delete";
		setResult(successMsg, null);
		return list();
	}


}
