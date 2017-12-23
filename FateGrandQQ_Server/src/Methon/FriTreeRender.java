package Methon;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

public class FriTreeRender extends DefaultTreeCellRenderer{
	private static final long serialVersionUID = 1L;
	ImageIcon Arrow_right = new ImageIcon("src/GUI/img/Arrow_right.png");//节点折叠时的图片
	ImageIcon Arrow_down = new ImageIcon("src/GUI/img/Arrow_down.png");//节点展开式的图片
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean
        expanded,boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, 
				leaf,row, hasFocus);
		this.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.setPreferredSize(new Dimension(360, 20));
		FriTreeNode f = (FriTreeNode) value;//把value转换为节点
		
		if (leaf && f.getParent() != tree.getModel().getRoot()) {//节点需要不为根节点，和根节点的孩子节点
			String text="<HTML><font color=#202021>"+f.getuName()+
					"</font><font color=#A9A9A9 size=4>【"+f.get_state()+
					"】</font><br/><font color=#778899 size=5>"+f.getText()+"</font></html>";
			setText(text);
			setIcon(f.getImg());// 设置JLable的图片
			setIconTextGap(20);// 设置JLable的图片与文字之间的距离
			setPreferredSize(new Dimension(338, 20));
		} else {
			setText(f.getuName());
			if (expanded)//节点展开
				setIcon(Arrow_down);
			else
				setIcon(Arrow_right);// 设置JLable的图片
		}
		
		return this;
	}
}
