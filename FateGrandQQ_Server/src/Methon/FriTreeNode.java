package Methon;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.tree.*;

import object.USER;

public class FriTreeNode extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 1L;
	private ImageIcon head;
	private String name;//第一行文字（显示名字）
	private String text;//第二行文字（显示签名）
	private String state;

	private ArrayList<TreeNode> children;//孩子节点
	private TreeNode parent;//父亲节点
	
	public int qq;
	
	public FriTreeNode() {	
		head=new ImageIcon(new ImageIcon("src/GUI/img/test.jpg").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
		name="测试";
		text="底部测试区正常";
		this.state="在线";
	}
	public FriTreeNode(USER user) {	
		head=new ImageIcon((new ImageIcon(user.head)).getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
		name=user.name;
		text=user.sign;
		this.state=user.state;
		this.qq=user.qq;
	}
	public FriTreeNode(String name) {	
		this.name=name;
	}
	
	public String getuName() {
		return name;
	}
	public void setuName(String name) {
		this.name = name;
	}
	
	public int get_qq() {
		return qq;
	}
	public String get_state() {
		return state;
	}
	
	public void setParent(FriTreeNode parent) {
		this.parent = parent;
	}

	
	public ImageIcon getImg() {
		return head;
	}
	public void setImg(ImageIcon img) {
		head=new ImageIcon(img.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public void addchild(FriTreeNode aChild){
		if(children==null){
			children=new ArrayList<TreeNode>();
		}
		children.add(aChild);
		aChild.parent=this;
	}
	public void addchild(USER user){
		if(children==null){
			children=new ArrayList<TreeNode>();
		}
		FriTreeNode aChild=new FriTreeNode(user);
		children.add(aChild);
		aChild.parent=this;
	}
	public void addchild(USER[] users){
		if(children==null){
			children=new ArrayList<TreeNode>();
		}
		for (int i = 0; i < users.length; i++) {
			FriTreeNode aChild=new FriTreeNode(users[i]);
			children.add(aChild);
			aChild.parent=this;
		}
	}
	
	public void addchild(ArrayList<USER> users){
		if(children==null){
			children=new ArrayList<TreeNode>();
		}
		for (int i = 0; i < users.size(); i++) {
			FriTreeNode aChild=new FriTreeNode(users.get(i));
			children.add(aChild);
			aChild.parent=this;
		}
	}
	
	public boolean isroot(){
		return (getParent()==null);
	}


	public TreeNode getChildAt(int childIndex) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}
		return children.get(childIndex) ;
	}


	public int getChildCount() {
		if (children == null) {
			return -1 ;
		}
		return children.size();
	}


	public TreeNode getParent() {
		return parent;
	}	

	public int getIndex(TreeNode aChild) {

		if (aChild == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(aChild)) {
			return -1;
		}
		return children.indexOf(aChild); 	
	}

	@Override
	public boolean getAllowsChildren() {

		return true;
	}

	/**
	 * 判断是否是叶子节点
	 */
	@Override
	public boolean isLeaf() {

		return (getChildCount() ==-1)&&(getParent()!=null);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children() {
		return null;
	}

	public boolean isNodeChild(TreeNode aNode) {
		boolean retval;

		if (aNode == null) {
			retval = false;
		} else {
			if (getChildCount() == 0) {
				retval = false;
			} else {
				retval = (aNode.getParent() == this);
			}
		}

		return retval;
	}
}