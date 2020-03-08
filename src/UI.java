import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class UI {
	private BufferedImage image,image1;
	private JFrame frame;
	JLabel lblNewLabel,lblNewLabel_1;
	JButton btnNewButton ,btnNewButton_1,btnNewButton_2;
	JSlider slider;
	JPanel panel;
	int value;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void add() {
		
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(btnNewButton_1);
		frame.getContentPane().add(slider);
		frame.getContentPane().add(btnNewButton_2);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 629, 758);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel=new JPanel();
		panel.setBounds(0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight());
		//frame.getContentPane().add(panel);
		lblNewLabel = new JLabel("原图");
		lblNewLabel.setBounds(270, 88, 300, 265);
		frame.getContentPane().add(lblNewLabel);
		//panel.add(lblNewLabel);
		lblNewLabel_1 = new JLabel("修改后");
		lblNewLabel_1.setBounds(270, 402, 300, 259);
		frame.getContentPane().add(lblNewLabel_1);
		//panel.add(lblNewLabel_1);
		btnNewButton = new JButton("导入图片");
		btnNewButton.setBounds(361, 369, 97, 23);
		frame.getContentPane().add(btnNewButton);
		//panel.add(btnNewButton);
		btnNewButton_1 = new JButton("导出图片");
		btnNewButton_1.setBounds(361, 671, 97, 23);
		frame.getContentPane().add(btnNewButton_1);
		//panel.add(btnNewButton_1);
		slider = new JSlider(0,1000,0);//为滑动条设置上限
		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(50);
		slider.setPaintTicks(true);
        slider.setPaintLabels(true);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(10, 121, 169, 445);
		frame.getContentPane().add(slider);
		btnNewButton_2 = new JButton("确定");
		btnNewButton_2.setBounds(46, 608, 97, 23);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton.addActionListener(new ActionListener() {
			//导入图片
			 public void actionPerformed(ActionEvent e){
			
		    		JFileChooser chooser = new JFileChooser();
		    		
		    		chooser.setCurrentDirectory(new File("."));
		    		int result = chooser.showOpenDialog(null);
		    		if(result == JFileChooser.APPROVE_OPTION){
		    			frame.getContentPane().removeAll();
		    			//frame.getContentPane().updateUI();  
		    			
		    			String ImagePath = chooser.getSelectedFile().getPath();
		    			try{
		    				File Image = new File(ImagePath);
		    				image = ImageIO.read(Image);
		    			}catch(IOException ex){
		    				ex.printStackTrace();
		    				
		    			} 
		    			if(image==null) {
							 JOptionPane.showMessageDialog(null, "错误格式，请重新选择 ", "警告 ", JOptionPane.ERROR_MESSAGE);
						 }else {
						
						ImageIcon icon1 = new ImageIcon(image);
						icon1.setImage(icon1.getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_DEFAULT));
						lblNewLabel.setIcon(icon1);
						lblNewLabel_1.setIcon(icon1);
						image1=image;
						lblNewLabel.updateUI();
						lblNewLabel_1.updateUI();
						frame.getContentPane().add(lblNewLabel, new Integer(Integer.MIN_VALUE));
						frame.getContentPane().add(lblNewLabel_1, new Integer(Integer.MIN_VALUE));
						frame.getContentPane().repaint();
						add();
						 }
		    		}
			 }
		});
		//导出图片
		btnNewButton_1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e){
				 if(image==null) {
					 JOptionPane.showMessageDialog(null, "未选择图片 ", "警告 ", JOptionPane.ERROR_MESSAGE);
				 }else {
					
					 JFileChooser chooser = new JFileChooser();
					 FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "jpg");
					 chooser.setFileFilter(filter);
					 chooser.showSaveDialog(null);
					 File path=chooser.getSelectedFile();
					 File f = new File(path+".jpg");
					 //System.out.println(path.toString());
			    	 try {
						ImageIO.write(image1, "jpg", f);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 }
			 }
		});
		//滑动条读取
		slider.addChangeListener(new ChangeListener() {
	            @Override
	            public void stateChanged(ChangeEvent e) {
	               // System.out.println("当前值: " + slider.getValue());
	                value=slider.getValue();
	            }
	        });
		//转化
		btnNewButton_2.addActionListener(new ActionListener() {
			//进行压缩
			 public void actionPerformed(ActionEvent e){
				 if(image==null) {
					 JOptionPane.showMessageDialog(null, "未选择图片 ", "警告 ", JOptionPane.ERROR_MESSAGE);
				 }else {
					int quality=value;
					Encode test=new Encode(image1, quality);
					ImageIcon icon1 = new ImageIcon(test.image);
					icon1.setImage(icon1.getImage().getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(), Image.SCALE_DEFAULT));
					lblNewLabel_1.setIcon(icon1);
					lblNewLabel_1.updateUI();
					frame.getContentPane().add(lblNewLabel_1, new Integer(Integer.MIN_VALUE));
					frame.getContentPane().repaint();
					add();
					JOptionPane.showMessageDialog(null, test.PSNR, "PSNR值为 ", JOptionPane.INFORMATION_MESSAGE);
				 }
			 }
		});
	}
}