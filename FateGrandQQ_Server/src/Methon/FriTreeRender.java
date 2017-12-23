package Methon;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

public class FriTreeRender extends DefaultTreeCellRenderer{
	private static final long serialVersionUID = 1L;
	ImageIcon Arrow_right = new ImageIcon("src/GUI/img/Arrow_right.png");//�ڵ��۵�ʱ��ͼƬ
	ImageIcon Arrow_down = new ImageIcon("src/GUI/img/Arrow_down.png");//�ڵ�չ��ʽ��ͼƬ
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean
        expanded,boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, 
				leaf,row, hasFocus);
		this.setFont(new Font("΢���ź�", Font.BOLD, 18));
		this.setPreferredSize(new Dimension(360, 20));
		FriTreeNode f = (FriTreeNode) value;//��valueת��Ϊ�ڵ�
		
		if (leaf && f.getParent() != tree.getModel().getRoot()) {//�ڵ���Ҫ��Ϊ���ڵ㣬�͸��ڵ�ĺ��ӽڵ�
			String text="<HTML><font color=#202021>"+f.getuName()+
					"</font><font color=#A9A9A9 size=4>��"+f.get_state()+
					"��</font><br/><font color=#778899 size=5>"+f.getText()+"</font></html>";
			setText(text);
			setIcon(f.getImg());// ����JLable��ͼƬ
			setIconTextGap(20);// ����JLable��ͼƬ������֮��ľ���
			setPreferredSize(new Dimension(338, 20));
		} else {
			setText(f.getuName());
			if (expanded)//�ڵ�չ��
				setIcon(Arrow_down);
			else
				setIcon(Arrow_right);// ����JLable��ͼƬ
		}
		
		return this;
	}
}
