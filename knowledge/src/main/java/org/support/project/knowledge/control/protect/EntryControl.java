package org.support.project.knowledge.control.protect;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.knowledge.control.KnowledgeControlBase;
import org.support.project.knowledge.dao.CommentsDao;
import org.support.project.knowledge.dao.KnowledgesDao;
import org.support.project.knowledge.dao.StockKnowledgesDao;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.dao.CategoriesDao;
import org.support.project.knowledge.dao.TemplateMastersDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.StockKnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.CategoriesEntity;
import org.support.project.knowledge.entity.TemplateItemsEntity;
import org.support.project.knowledge.entity.TemplateMastersEntity;
import org.support.project.knowledge.logic.GroupLogic;
import org.support.project.knowledge.logic.KnowledgeLogic;
import org.support.project.knowledge.logic.TargetLogic;
import org.support.project.knowledge.logic.UploadedFileLogic;
import org.support.project.knowledge.vo.Stock;
import org.support.project.knowledge.vo.UploadFile;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.entity.GroupsEntity;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public class EntryControl extends KnowledgeControlBase {
	/** ログ */
	private static Log LOG = LogFactory.getLog(EntryControl.class);

	private KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
	private UploadedFileLogic fileLogic = UploadedFileLogic.get();

	/**
	 * 登録画面を表示する
	 * @return
	 */
	@Get
	public Boundary view_add() {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();

		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);

		List<TagsEntity> tagitems = TagsDao.get().selectAll();
		setAttribute("tagitems", tagitems);

		List<CategoriesEntity> categories = CategoriesDao.get().selectAll();
		setAttribute("categories", categories);

//		if (categories.size()>0) {
//			setAttribute("categoryId", categories.get(0).getCategoryId());
//		}

		List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
		setAttribute("templates", templates);

		setAttribute("typeId", KnowledgeLogic.TEMPLATE_TYPE_KNOWLEDGE);

		// グループが指定されてる場合はデフォルトで公開範囲と共同編集者を選択済みにする
		String groupId = super.getParam("group", String.class);
		if (StringUtils.isNotEmpty(groupId)) {
			GroupsEntity group = GroupLogic.get().getGroup(new Integer(groupId), getLoginedUser());
			if (group == null) {
				return sendError(HttpStatus.SC_403_FORBIDDEN, "");
			}

			String[] groupIds = { TargetLogic.ID_PREFIX_GROUP + groupId };
			List<LabelValue> targets = TargetLogic.get().selectTargets(groupIds);
			setAttribute("publicFlag", KnowledgeLogic.PUBLIC_FLAG_PROTECT);
			setAttribute("groups", targets);
			setAttribute("editors", targets);
		}

		return forward("view_add.jsp");
	}
	/**
	 * 更新画面を表示する
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	public Boundary view_edit() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();

		String offset = super.getParam("offset", String.class);
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		setAttribute("offset", offset);

		Long knowledgeId = super.getPathLong();
		KnowledgesEntity entity = knowledgeLogic.selectWithTags(knowledgeId, getLoginedUser());
		if (entity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		setAttributeOnProperty(entity);

		// ナレッジに紐づく添付ファイルを取得
		List<UploadFile> files = fileLogic.selectOnKnowledgeIdWithoutCommentFiles(knowledgeId, getRequest().getContextPath());
		setAttribute("files", files);

		// 表示するグループを取得
		List<LabelValue> groups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgeId);
		setAttribute("groups", groups);

		// 共同編集者
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
		setAttribute("editors", editors);
		// 編集権限チェック
		LoginedUser loginedUser = super.getLoginedUser();
		boolean edit = knowledgeLogic.isEditor(loginedUser, entity, editors);
		if (!edit) {
			setAttribute("edit", false);
			addMsgWarn("knowledge.edit.noaccess");
			//return forward("/open/entry/view.jsp");
			return devolution(HttpMethod.get, "open.Entry/view", String.valueOf(knowledgeId));
		}

		List<TagsEntity> tagitems = TagsDao.get().selectAll();
		setAttribute("tagitems", tagitems);

		List<CategoriesEntity> categories = CategoriesDao.get().selectAll();
		setAttribute("categories", categories);

		List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
		setAttribute("templates", templates);

		return forward("view_edit.jsp");
	}

	/**
	 * 登録する
	 * APIによる保存とし、画面遷移は行わない
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	@Post
	public Boundary add(KnowledgesEntity entity) throws Exception, ParseException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();

		String groupsstr = super.getParam("groups");
		String[] targets = groupsstr.split(",");
		//List<GroupsEntity> groups = GroupLogic.get().selectGroups(targets);
		List<LabelValue> groups = TargetLogic.get().selectTargets(targets);
		setAttribute("groups", groups);

		String editorsstr = super.getParam("editors");
		String[] editordids = editorsstr.split(",");
		List<LabelValue> editors = TargetLogic.get().selectTargets(editordids);
		setAttribute("editors", editors);

		List<TagsEntity> tagitems = TagsDao.get().selectAll();
		setAttribute("tagitems", tagitems);

		List<Long> fileNos = new ArrayList<Long>();
		String[] filesArray = getParam("files", String[].class);
		if (filesArray != null) {
			for (String string : filesArray) {
				if (StringUtils.isLong(string)) {
					fileNos.add(new Long(string));
				}
			}
		}

		List<CategoriesEntity> categories = CategoriesDao.get().selectAll();
		setAttribute("categories", categories);

		List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
		setAttribute("templates", templates);

		TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(entity.getTypeId());
		List<TemplateItemsEntity> items = template.getItems();
		for (TemplateItemsEntity item : items) {
			String itemValue = super.getParam("item_" + item.getItemNo());
			if (itemValue.startsWith("[") && itemValue.endsWith("]")) {
				itemValue = itemValue.substring(1, itemValue.length() -1);
				item.setItemValue(itemValue);
			} else {
				item.setItemValue(itemValue);
			}
		}

		List<ValidateError> errors = entity.validate();
		if (!errors.isEmpty()) {
			//入力エラー
			return sendValidateError(errors);
		}
		LOG.trace("save");
		String tags = super.getParam("tagNames");
		List<TagsEntity> tagList = knowledgeLogic.manegeTags(tags);

		entity = knowledgeLogic.insert(entity, tagList, fileNos, groups, editors, template, super.getLoginedUser());
		setAttributeOnProperty(entity);

		setCookie(SystemConfig.COOKIE_KEY_CATEGORY,String.valueOf(entity.getCategoryId()));

		List<UploadFile> files = fileLogic.selectOnKnowledgeIdWithoutCommentFiles(entity.getKnowledgeId(), getRequest().getContextPath());
		setAttribute("files", files);

		addMsgSuccess("message.success.insert");
//		return forward("view_edit.jsp");
		return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(entity.getKnowledgeId()), "message.success.insert");
	}

	/**
	 * 更新する
	 * APIによる保存とし、画面遷移は行わない
	 * @return
	 * @throws Exception
	 */
	@Post
	public Boundary update(KnowledgesEntity entity) throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();

		String groupsstr = super.getParam("groups");
		String[] targets = groupsstr.split(",");
		List<LabelValue> groups = TargetLogic.get().selectTargets(targets);
		setAttribute("groups", groups);

		String editorsstr = super.getParam("editors");
		String[] editordids = editorsstr.split(",");
		List<LabelValue> editors = TargetLogic.get().selectTargets(editordids);
		setAttribute("editors", editors);

		List<TagsEntity> tagitems = TagsDao.get().selectAll();
		setAttribute("tagitems", tagitems);

		List<Long> fileNos = new ArrayList<Long>();
		String[] filesArray = getParam("files", String[].class);
		if (filesArray != null) {
			for (String string : filesArray) {
				if (StringUtils.isLong(string)) {
					fileNos.add(new Long(string));
				}
			}
		}

		List<CategoriesEntity> categories = CategoriesDao.get().selectAll();
		setAttribute("categories", categories);

		List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
		setAttribute("templates", templates);

		TemplateMastersEntity template = TemplateMastersDao.get().selectWithItems(entity.getTypeId());
		List<TemplateItemsEntity> items = template.getItems();
		for (TemplateItemsEntity item : items) {
			String itemValue = super.getParam("item_" + item.getItemNo());
			if (itemValue.startsWith("[") && itemValue.endsWith("]")) {
				itemValue = itemValue.substring(1, itemValue.length() -1);
				item.setItemValue(itemValue);
			} else {
				item.setItemValue(itemValue);
			}
		}

		KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
		List<ValidateError> errors = entity.validate();
		if (!errors.isEmpty()) {
			//入力エラー
			return sendValidateError(errors);
		}

		KnowledgesEntity check = dao.selectOnKey(entity.getKnowledgeId());
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		// 編集権限チェック
		if (!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors)) {
			setAttribute("edit", false);
			addMsgWarn("knowledge.edit.noaccess");
//			return devolution(HttpMethod.get, "open.Entry/view", String.valueOf(entity.getKnowledgeId()));

			errors = new ArrayList<>();
			errors.add(new ValidateError("knowledge.edit.noaccess"));
			return sendValidateError(errors);
		}

		LOG.trace("save");
		String tags = super.getParam("tagNames");
		List<TagsEntity> tagList = knowledgeLogic.manegeTags(tags);

		entity = knowledgeLogic.update(entity, tagList, fileNos, groups, editors, template, super.getLoginedUser());
		setAttributeOnProperty(entity);
		addMsgSuccess("message.success.update");

		setCookie(SystemConfig.COOKIE_KEY_CATEGORY,String.valueOf(entity.getCategoryId()));

		List<UploadFile> files = fileLogic.selectOnKnowledgeIdWithoutCommentFiles(entity.getKnowledgeId(), getRequest().getContextPath());
		setAttribute("files", files);

		// return forward("view_edit.jsp");
		return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(entity.getKnowledgeId()), "message.success.update");
	}

	/**
	 * ナレッジを削除
	 * @return
	 * @throws Exception
	 */
	@Post
	public Boundary delete() throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();

		LOG.trace("validate");
		KnowledgesDao dao = Container.getComp(KnowledgesDao.class);
		String id = getParam("knowledgeId");
		if (!StringUtils.isInteger(id)) {
			// 削除するIDが指定されていない
			//return sendError(HttpStatus.SC_400_BAD_REQUEST, null);
			addMsgError("knowledge.delete.none");
			//return super.devolution("open.entry/list");
			return forward("/commons/errors/server_error.jsp");
		}

		List<TagsEntity> tagitems = TagsDao.get().selectAll();
		setAttribute("tagitems", tagitems);

		List<CategoriesEntity> categories = CategoriesDao.get().selectAll();
		setAttribute("categories", categories);

		List<TemplateMastersEntity> templates = TemplateMastersDao.get().selectAll();
		setAttribute("templates", templates);

		Long knowledgeId = new Long(id);
		KnowledgesEntity check = dao.selectOnKey(knowledgeId);
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(knowledgeId);
		if (!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors)) {
			setAttribute("edit", false);
			addMsgWarn("knowledge.edit.noaccess");
			//return forward("/open/entry/view.jsp");
			return devolution(HttpMethod.get, "open.Entry/view", String.valueOf(knowledgeId));
		}
		LOG.trace("save");
		knowledgeLogic.delete(knowledgeId);

		addMsgSuccess("message.success.delete");
		return super.devolution(HttpMethod.get, "open.Entry/list");
	}

	/**
	 * ログイン後、表示しなおし
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	public Boundary view() throws InvalidParamException {
		// 共通処理呼の表示条件の保持の呼び出し
		setViewParam();

		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		return super.redirect(getRequest().getContextPath() + "/open.entry/view/" + knowledgeId);
	}

	/**
	 * コメント追加
	 * @return
	 * @throws Exception
	 */
	@Post
	public Boundary comment() throws Exception {
		// 共通処理呼の表示条件の保持の呼び出し
		String params = setViewParam();
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		//String comment = super.sanitize(getParam("addcomment"));

		List<Long> fileNos = new ArrayList<Long>();
		Object obj = getParam("files", Object.class);
		if (obj != null) {
			if (obj instanceof String) {
				String string = (String) obj;
				if (StringUtils.isLong(string)) {
					fileNos.add(new Long(string));
				}
			} else if (obj instanceof List) {
				List<String> strings = (List<String>) obj;
				for (String string : strings) {
					if (StringUtils.isLong(string)) {
						fileNos.add(new Long(string));
					}
				}
			}
		}

		String comment = getParam("addcomment");

		// 必須チェック
		if (StringUtils.isEmpty(comment)) {
			addMsgWarn("errors.required", "Comment");

			// バリデーションエラーが発生した場合、設定されていた添付ファイルの情報は再取得
			List<UploadFile> files = fileLogic.selectOnFileNos(fileNos, getRequest().getContextPath());
			Iterator<UploadFile> iterator = files.iterator();
			while (iterator.hasNext()) {
				UploadFile uploadFile = (UploadFile) iterator.next();
				if (uploadFile.getKnowlegeId() != null) {
					// 新規登録なのに、添付ファイルが既にナレッジに紐づいている（おかしい）
					iterator.remove();
				}
			}
			setAttribute("comment_files", files);

			return super.devolution(HttpMethod.get, "open.Entry/view", String.valueOf(knowledgeId));
		}

		KnowledgeLogic.get().saveComment(knowledgeId, comment, fileNos, getLoginedUser());

		return super.redirect(getRequest().getContextPath() + "/open.entry/view/" + knowledgeId + params);
	}


	/**
	 * 指定のナレッジにアクセスできる対象を取得
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	public Boundary view_targets() throws InvalidParamException {
		Long knowledgeId = super.getPathLong(Long.valueOf(-1));
		List<LabelValue> groups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledgeId);
		return super.send(groups);
	}


	/**
	 * コメント編集画面を表示
	 * @return
	 * @throws InvalidParamException
	 */
	@Get
	public Boundary edit_comment() throws InvalidParamException {
		Long commentNo = super.getPathLong(Long.valueOf(-1));
		CommentsDao commentsDao = CommentsDao.get();
		CommentsEntity commentsEntity = commentsDao.selectOnKey(commentNo);

		if (commentsEntity == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}

//		// 権限チェック（ナレッジが表示できること）
//		KnowledgeLogic knowledgeLogic = KnowledgeLogic.get();
//		KnowledgesEntity entity = knowledgeLogic.select(commentsEntity.getKnowledgeId(), getLoginedUser());
//		if (entity == null) {
//			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT FOUND");
//		}

		// 権限チェック（コメントの編集は、システム管理者 or コメントの登録者 or ナレッジ編集者
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			// ログインしていないユーザに編集権限は無し
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		if (!loginedUser.isAdmin() &&
				loginedUser.getUserId().intValue() != commentsEntity.getInsertUser().intValue()) {
			KnowledgesEntity check = KnowledgesDao.get().selectOnKey(commentsEntity.getKnowledgeId());
			if (check == null) {
				return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
			}
			List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(commentsEntity.getKnowledgeId());
			if (!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors)) {
				return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
			}
		}

		// ナレッジに紐づく添付ファイルを取得
		List<UploadFile> files = fileLogic.selectOnKnowledgeId(commentsEntity.getKnowledgeId(), getRequest().getContextPath());
		List<UploadFile> commentFiles = new ArrayList<>();
		for (UploadFile uploadFile : files) {
			if (commentsEntity.getCommentNo().equals(uploadFile.getCommentNo())) {
				commentFiles.add(uploadFile);
			}
		}
		setAttribute("comment_files", commentFiles);

		setAttributeOnProperty(commentsEntity);
		return forward("edit_comment.jsp");
	}

	/**
	 * コメントを更新
	 * @return
	 * @throws Exception
	 */
	@Post
	public Boundary update_comment() throws Exception {
		List<Long> fileNos = new ArrayList<Long>();
		Object obj = getParam("files", Object.class);
		if (obj != null) {
			if (obj instanceof String) {
				String string = (String) obj;
				if (StringUtils.isLong(string)) {
					fileNos.add(new Long(string));
				}
			} else if (obj instanceof List) {
				List<String> strings = (List<String>) obj;
				for (String string : strings) {
					if (StringUtils.isLong(string)) {
						fileNos.add(new Long(string));
					}
				}
			}
		}
		// 設定されていた添付ファイルの情報は再取得
		List<UploadFile> files = fileLogic.selectOnFileNos(fileNos, getRequest().getContextPath());
		Iterator<UploadFile> iterator = files.iterator();
		while (iterator.hasNext()) {
			UploadFile uploadFile = (UploadFile) iterator.next();
			if (uploadFile.getKnowlegeId() != null) {
				// 新規登録なのに、添付ファイルが既にナレッジに紐づいている（おかしい）
				iterator.remove();
			}
		}
		setAttribute("comment_files", files);


		CommentsEntity commentsEntity = getParamOnProperty(CommentsEntity.class);

		CommentsDao commentsDao = CommentsDao.get();
		CommentsEntity db = commentsDao.selectOnKey(commentsEntity.getCommentNo());

		if (db == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		// 権限チェック（コメントの編集は、システム管理者 or コメントの登録者 or ナレッジの編集可能ユーザ
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			// ログインしていないユーザに編集権限は無し
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		KnowledgesEntity check = KnowledgesDao.get().selectOnKey(db.getKnowledgeId());
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(db.getKnowledgeId());
		if (!loginedUser.isAdmin()) {
			if (loginedUser.getUserId().intValue() != db.getInsertUser().intValue() &&
					!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors))
				return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}

		// 必須チェック
		if (StringUtils.isEmpty(commentsEntity.getComment())) {
			addMsgWarn("errors.required", "Comment");
			return super.devolution(HttpMethod.get, "/protect.knowledge/edit_comment", String.valueOf(commentsEntity.getCommentNo()));
		}
		db.setComment(commentsEntity.getComment());
		KnowledgeLogic.get().updateComment(db, fileNos, getLoginedUser());
		setAttributeOnProperty(db);

		addMsgSuccess("message.success.update");

		setPathInfo(String.valueOf(commentsEntity.getCommentNo()));
		return edit_comment();
	}


	/**
	 * コメントを削除
	 * @return
	 * @throws Exception
	 */
	@Get
	public Boundary delete_comment() throws Exception {
		Long commentNo = super.getPathLong(Long.valueOf(-1));
		CommentsDao commentsDao = CommentsDao.get();
		CommentsEntity db = commentsDao.selectOnKey(commentNo);

		if (db == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		// 権限チェック（コメントの削除は、システム管理者 or コメントの登録者 or ナレッジの編集可能ユーザ
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			// ログインしていないユーザに編集権限は無し
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		KnowledgesEntity check = KnowledgesDao.get().selectOnKey(db.getKnowledgeId());
		if (check == null) {
			return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
		}
		List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(db.getKnowledgeId());

		if (!loginedUser.isAdmin()) {
			if (loginedUser.getUserId().intValue() != db.getInsertUser().intValue() &&
					!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors))
				return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}

		KnowledgeLogic.get().deleteComment(db, getLoginedUser());
		addMsgSuccess("message.success.delete.target", getResource("label.comment"));
		setAttribute("comment", null);
		return devolution(HttpMethod.get, "open.Entry/view", String.valueOf(db.getKnowledgeId()));
	}




	/**
	 * ナレッジをストックに保存
	 * @return
	 * @throws IOException
	 * @throws InvalidParamException
	 */
	@Post
	public Boundary stock() throws IOException, InvalidParamException {
		Long knowledgeId = getPathLong();
		if (LOG.isTraceEnabled()) {
			LOG.trace(knowledgeId);
		}

		List<Stock> stocks = new ArrayList<>();
		BufferedReader reader = getRequest().getReader();
		try {
			List<Map<String, String>> json = JSON.decode(reader);
			for (Map<String, String> map : json) {
				Stock stock = new Stock();
				Iterator<String> keys = map.keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					Object value = map.get(key);
					if (LOG.isTraceEnabled()) {
						LOG.trace(key + " = " + value + "  (" + value.getClass().getName() + ")");
					}
					Object val = PropertyUtil.convValue(value.toString(), PropertyUtil.getPropertyType(stock, key));
					PropertyUtil.setPropertyValue(stock, key, val);
				}
				stocks.add(stock);
				if (LOG.isTraceEnabled()) {
					LOG.trace(PropertyUtil.reflectionToString(stock));
				}
			}
		} finally {
			reader.close();
		}

		StockKnowledgesDao dao = StockKnowledgesDao.get();
		for (Stock stock : stocks) {
			StockKnowledgesEntity entity = new StockKnowledgesEntity();
			entity.setStockId(stock.getStockId());
			entity.setKnowledgeId(knowledgeId);
			entity.setComment(stock.getDescription());
			if (stock.getStocked()) {
				dao.save(entity);
			} else {
				dao.physicalDelete(entity);
			}
		}
		return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, "saved", "message.success.save");
	};


	/**
	 * コメントを折りたたみ
	 * @return
	 * @throws IOException
	 * @throws InvalidParamException
	 */
	@Post
	public Boundary collapse() throws IOException, InvalidParamException {
		Long commentNo = getParam("commentNo", Long.class);
		Integer collapse = getParam("collapse", Integer.class);

		CommentsDao commentsDao = CommentsDao.get();
		CommentsEntity db = commentsDao.selectOnKey(commentNo);

		// 権限チェック（コメントの編集は、システム管理者 or コメントの登録者 or ナレッジ編集者
		LoginedUser loginedUser = super.getLoginedUser();
		if (loginedUser == null) {
			// ログインしていないユーザに編集権限は無し
			return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
		}
		if (!loginedUser.isAdmin() &&
				loginedUser.getUserId().intValue() != db.getInsertUser().intValue()) {
			KnowledgesEntity check = KnowledgesDao.get().selectOnKey(db.getKnowledgeId());
			if (check == null) {
				return sendError(HttpStatus.SC_404_NOT_FOUND, "NOT_FOUND");
			}
			List<LabelValue> editors = TargetLogic.get().selectEditorsOnKnowledgeId(db.getKnowledgeId());
			if (!knowledgeLogic.isEditor(super.getLoginedUser(), check, editors)) {
				return sendError(HttpStatus.SC_403_FORBIDDEN, "FORBIDDEN");
			}
		}

		// ステータス更新
		db.setCommentStatus(collapse);
		commentsDao.physicalUpdate(db); // 更新履歴は付けないで更新

		if (collapse == 1) {
			return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(commentNo), "knowledge.view.comment.collapse.on");
		} else {
			return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK, String.valueOf(commentNo), "knowledge.view.comment.collapse.off");
		}
	}



}




