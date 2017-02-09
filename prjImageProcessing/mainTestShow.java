
public class mainTestShow {
	 public static void main(String[] args) {
		 //showing image
		  PixImage image1 = new PixImage(3,3);
				 image1=image1.array2PixImage(new int[][] { { 0, 10, 240 },
             { 30, 120, 250 },
             { 80, 250, 255 } });
		 //testing in part 1
		 System.out .println("Testing on testing number image.\n");
		 System.out.println("Input image:");
		 System.out.print(image1+"\n");
		 //boxSmoothing
		 System.out.println("BoxSmoothingImage:");
		 System.out.println(image1.boxBlur(1));
		 System.out.println("GaussianFilterImage:");
		 System.out.println(image1.GaussianFilter(1));
		 System.out.println("PrewittMaskImage:");
		 System.out.println(image1.PrewittMask());
		 System.out.println("sobelEdgesImage:");
		 System.out.println(image1.sobelEdges());
		 
		 //testing on part 2 of the real images
		 args=new String[2];
		 for(int i=0;i<3;i++){
			 switch(i){
			 case 0:args[0]="flower.tiff";break;
			 case 1:args[0]="highcontrast.tiff";break;
			 case 2:args[0]="reggie.tiff";break;
			 }
			 
	         
			 args[1]="1";
			 Blur a=new Blur();
			 a.main(args);
			 
			 args[1]="0";
			 Sobel b=new Sobel();
			 b.main(args);
		 }

	 }
}
