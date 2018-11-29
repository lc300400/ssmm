package com.jjxc.modules.security.service;
  
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;  
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjxc.commons.ServiceException;
import com.jjxc.commons.Tree;
import com.jjxc.modules.security.dao.DeptDao;
import com.jjxc.modules.security.dao.UserDao;
import com.jjxc.modules.security.entity.Dept;
import com.jjxc.modules.security.entity.User;

@Service  
public class DeptService{  
	
	private static Logger logger = LoggerFactory.getLogger(DeptService.class);
    
	@Resource  
    private DeptDao deptDao;
	@Resource  
	private UserDao userDao;

	public Dept getById(Integer id) {
		return deptDao.queryById(id);
	}

	/**
	 * 查询没有上级部门的一级部门
	 */
	public List<Dept> findFirstLevel() {
		return deptDao.findFirstLevel();
	}
	
	/**
	 * 保存部门信息,如果部门名称重复或上级部门类别不符合规定将抛出异常.
	 */
	public void save(Dept dept){
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		if(!isNameUnique(dept)){
			logger.warn("操作员{}尝试保存已经存在的部门{}", new Object[]{currentUsername, dept.getName()});
			throw new ServiceException(String.format("名称为%s的部门已经存在", dept.getName()));
		}
		// 系统为没有填写显示顺序的部门自动分配顺序号.
		if(dept.getSeq() == null) {
			Integer pid = null;
			if(dept.getParent() != null) {
				pid = dept.getParent().getId();
			}
			String seq = deptDao.getMaxSeq(pid);
			if(seq == null){
				dept.setSeq(1);
			}else{
				dept.setSeq(Integer.parseInt(deptDao.getMaxSeq(pid)) + 1);
			}
		}
		// 设置新部门的部门编码
		if(dept.getId() == null) {
			Integer pid = null;
			String pNumber = null;
			if(dept.getParent() != null) {
				pid = dept.getParent().getId();
				pNumber = deptDao.queryById(pid).getNumber();
			}
			String newNumber = generateNumber(pid, pNumber);
			dept.setNumber(newNumber);
			deptDao.insertDept(dept);
		}else{
			deptDao.updateDept(dept);
		}
		logger.info("操作员{}保存部门信息 [ID={},部门名称={},部门编号={}]", new Object[]{currentUsername, dept.getId(), dept.getName(), dept.getNumber()});
	};
	
	/**
	 * 检查部门名称在同级部门中是否唯一.
	 */
	private boolean isNameUnique(Dept dept) {
		List<Dept> deptList = null;
		if(dept.getParent() == null) {
			deptList = deptDao.findFirstLevel();
		}else{
			Dept parent = deptDao.queryById(dept.getParent().getId());
			deptList = parent.getChildren();
		}
		if(deptList == null || deptList.size() == 0) {
			return true;
		}
		for(Dept tmp : deptList) {
			if(tmp.getName().equals(dept.getName()) && !tmp.getId().equals(dept.getId())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 根据上级部门,生成新的下级部门编号.
	 * @param pid 上级部门ID
	 * @param pNumber 上级部门编号
	 * @return 九位的部门编号
	 */
	private String generateNumber(Integer pid, String pNumber){
		String number = null;
		String maxNumber = deptDao.getMaxNumber(pid);
		if(pNumber == null) {
			if(maxNumber == null) {
				number = "000000000";
			} else {
				String level1 = maxNumber.substring(0,3);
				String levelNo = String.valueOf(Integer.valueOf(level1) + 1);
				while(levelNo.length() < 3) {
					levelNo += "0";
				}
				number = levelNo + "000000";
			}
		} else {
			String level = "000";
			String level1 = pNumber.substring(0,3);
			String level2 = pNumber.substring(3,6);
			String level3 = pNumber.substring(6,9);
			String levelNo;
			if(level.equals(level1)) {
				if(maxNumber == null) {
					number = "001000000";
				} else {
					level1 = maxNumber.substring(0,3);
					levelNo = String.valueOf(Integer.valueOf(level1) + 1);
					while(levelNo.length() < 3) {
						levelNo = "0" + levelNo;
					}
					number = levelNo + "000000";
				}
			} else if(level.equals(level2)) {
				if(maxNumber == null) {
					number = level1 + "001000";
				} else {
					level2 = maxNumber.substring(3,6);
					levelNo = String.valueOf(Integer.valueOf(level2) + 1);
					while(levelNo.length() < 3) {
						levelNo = "0" + levelNo;
					}
					number = level1 + levelNo + "000";
				}
			} else if(level.equals(level3)) {
				if(maxNumber == null) {
					number = level1 + level2 + "001";
				} else {
					level3 = maxNumber.substring(6,9);
					levelNo = String.valueOf(Integer.valueOf(level3) + 1);
					while(levelNo.length() < 3) {
						levelNo = "0" + levelNo;
					}
					number = level1 + level2 + levelNo;
				}
			} else {
				throw new ServiceException("新增部门失败,系统最多支持四级部门.");
			}
		}
		return number;
	}
	
	//删除部门信息
	//如果尝试删除存在下级部门或存在用户的部门将抛出异常
	public void delete(Integer id) {
		String currentUsername = (String) SecurityUtils.getSubject().getPrincipal();
		Dept dept = deptDao.queryById(id);
		if(dept == null) {
			logger.warn("操作员{}尝试删除不存在的部门 ID={}", new Object[]{currentUsername, id});
			return;
		}
		if(dept.getChildren() != null && dept.getChildren().size() > 0) {
			logger.warn("操作员{}尝试删除存在下级部门的部门 {}", new Object[]{currentUsername, dept.getName()});
			throw new ServiceException("不能删除存在下级部门的部门");
		}
		List<User> user = userDao.queryByDeptId(id);
		if(user.size()>0) {
			logger.warn("操作员{}尝试删除存在用户的部门 {}", new Object[]{currentUsername,dept.getName()});
			throw new ServiceException("不能删除存在用户的部门");
		}
		deptDao.deleteDept(id);
		logger.info("操作员{}删除部门 [ID={},部门名称={},部门编号={}]", new Object[]{currentUsername, id, dept.getName(), dept.getNumber()});
	}
	
	/**
	 * 封装JSON格式的EasyUI部门树.
	 */
	@Transactional(readOnly = true)
	public String getEasyUITree() {
		List<Dept> deptList = deptDao.findFirstLevel();
		List<Tree> treeList = new ArrayList<Tree>();
		for(Dept dept : deptList) {
			Tree tree = new Tree();
			tree.setId(dept.getId().toString());
			tree.setText(dept.getName());
			tree.setChildren(getEasyUIChildrenTree(dept));
			tree.setPid(dept.getParent() == null ? "" : dept.getParent().getId().toString());
			JSONObject attributes = new JSONObject();
			attributes.put("number", dept.getNumber());
			tree.setAttributes(attributes);
			treeList.add(tree);
		}
		return JSON.toJSONString(treeList);
	}

	/**
	 * 封装指定部门的下级EasyUI部门树列表.
	 */
	private List<Tree> getEasyUIChildrenTree(Dept dept) {
		List<Dept> childrenDeptList = dept.getChildren();
		List<Tree> childrenTreeList = new ArrayList<Tree>();
		if(childrenDeptList != null && childrenDeptList.size() > 0) {
			for(Dept children : childrenDeptList) {
				Tree tree = new Tree();
				tree.setId(children.getId().toString());
				tree.setText(children.getName());
				tree.setChildren(getEasyUIChildrenTree(children));
				tree.setPid(dept.getId().toString());
				if(!tree.getChildren().isEmpty()) {
					tree.setState("closed");
				}
				JSONObject attributes = new JSONObject();
				attributes.put("number", children.getNumber());
				tree.setAttributes(attributes);
				childrenTreeList.add(tree);
			}
		}
		return childrenTreeList;
	}
  
} 