import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class Getcolor
{
        String Filename;
        BufferedImage image;
        BufferedImage tmpIma;
        JFrame jf;        
        public static void main(String argv[])
        {
                new Getcolor();                            
        }
        public Getcolor()
        {
                LoadFile();
                ChangPix();
               // SetTable();
        }
        public void LoadFile()
        {
                Filename="b.bmp";               
                try
                {
                        image=ImageIO.read(new File(Filename));
                }
                catch(Exception e)
                {
                        javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: "+Filename);
                        image=null;
                }
        }
        public void ChangPix(){ 
        	File writename = new File("output.txt"); // 相對路徑，如果沒有則要建立一個新的output。txt檔案
        	File ToAsm =new File("ToAsm.txt");
        	int buf;
        	int counter=0;       
        	String a;
        	String[] str=new String[10];
            try {
				writename.createNewFile();
				ToAsm.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(writename));
				BufferedWriter asm_out=new BufferedWriter(new FileWriter(ToAsm));
				tmpIma=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
				asm_out.write("WrBullet proc\r\n");	
				asm_out.write("mov dx,y\r\n");
				asm_out.write("dec dx\r\n");
                for(int i=0;i<image.getHeight();i++){
                	out.write("\r\n"); // \r\n即為換行
                	asm_out.write("add dx,2\r\n");
                	asm_out.write("mov cx,x\r\n");
                	counter=0;
                        for(int j=0;j<image.getWidth();j++){
                                Color color=new Color(image.getRGB(j,i));
                                a=Integer.toHexString(color.getRed())+Integer.toHexString(color.getGreen())+Integer.toHexString(color.getBlue());                                
                               
                                
                                for(int z=0;z<10;z++)
                                	if(str[z]==null) 
                                	{
                                		str[z]=a;
                                		break;
                                	}                                		
                                	else
                                		if(str[z].equals(a))
                                			break;
                                out.write(a);                                
                                out.write(' ');
                                counter++;                                
                                if(a.equals("c0c0c0"))
                                {
                                	a="add cx,"+Integer.toString(counter)+"\r\n";
                                	asm_out.write(a);
                                	counter=0;
                                	asm_out.write("mov ax,color\r\n");
                                	asm_out.write("call WrPixel\r\n");
                                }
//                                else if(a.equals("808080")) 
//                                {
//                                	a="add cx,"+Integer.toString(counter)+"\r\n";
//                                	asm_out.write(a);
//                                	counter=0;
//                                	asm_out.write("mov ax,color\r\n");
//                                	asm_out.write("call WrPixel\r\n");
//                                }
//                                else if(a.equals("0800")) 
//                                {
//                                	a="add cx,"+Integer.toString(counter)+"\r\n";
//                                	asm_out.write(a);
//                                	counter=0;
//                                	asm_out.write("mov ax,color\r\n");
//                                	asm_out.write("call WrPixel\r\n");
//                                }
//                                else if(a.equals("80800")) 
//                                {
//                                	a="add cx,"+Integer.toString(counter)+"\r\n";
//                                	asm_out.write(a);
//                                	counter=0;
//                                	asm_out.write("mov ax,color\r\n");
//                                	asm_out.write("call WrPixel\r\n");
//                                }
                                else if(a.equals("000")) 
                                {
                                	a="add cx,"+Integer.toString(counter)+"\r\n";
                                	asm_out.write(a);
                                	counter=0;
                                	asm_out.write("mov ax,color\r\n");
                                	asm_out.write("call WrPixel\r\n");
                                }
//                                else if(a.equals("ffffff")) 
//                                {
//                                	a="add cx,"+Integer.toString(counter)+"\r\n";
//                                	asm_out.write(a);
//                                	counter=0;
//                                	asm_out.write("mov ax,0Fh\r\n");
//                                	asm_out.write("call WrPixel\r\n");
//                                }
//                                else if(a.equals("8000")) 
//                                {
//                                	a="add cx,"+Integer.toString(counter)+"\r\n";
//                                	asm_out.write(a);
//                                	counter=0;
//                                	asm_out.write("mov ax,4h\r\n");
//                                	asm_out.write("call WrPixel\r\n");
//                                }
//                                else if(a.equals("0080")) 
//                                {
//                                	a="add cx,"+Integer.toString(counter)+"\r\n";
//                                	asm_out.write(a);
//                                	counter=0;
//                                	asm_out.write("mov ax,1h\r\n");
//                                	asm_out.write("call WrPixel\r\n");
//                                }
                                else {}
                                
                                int tmp=(color.getRed()+color.getGreen()+color.getBlue())/3;
                                Color tmpcol=new Color(tmp,tmp,tmp);
                                tmpIma.setRGB(j,i,tmpcol.getRGB());
                        }
                }
                for(int z=0;z<10;z++)
                	if(str[z]!=null)
                		out.write(str[z]+" ");
                	else
                		break;
                out.flush(); // 把快取區內容壓入檔案
                out.close(); // 最後記得關閉檔案         
                asm_out.write("ret\r\n");
                asm_out.write("WrBullet endp\r\n");
                asm_out.flush(); // 把快取區內容壓入檔案
                asm_out.close(); // 最後記得關閉檔案
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 建立新檔案    
                
        }
        public void SetTable()
        {
                jf = new JFrame("");
                JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(tmpIma)));
                jf.getContentPane().add(scrollPane);
                jf.pack();
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jf.setTitle(Filename+" "+image.getWidth()+" x "+image.getHeight());
                jf.setLocationRelativeTo(null);
                jf.setVisible(true);
        }

}