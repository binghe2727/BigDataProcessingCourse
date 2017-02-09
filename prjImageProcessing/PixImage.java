/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

public class PixImage {

  /**
   *  Define any variables associated with a PixImage object here.  These
   *  variables MUST be private.
   */
	int width; 
	int height;
	short[][][] PixImage;



  /**
   * PixImage() constructs an empty PixImage with a specified width and height.
   * Every pixel has red, green, and blue intensities of zero (solid black).
   *
   * @param width the width of the image.
   * @param height the height of the image.
   */
  public PixImage(int width, int height) {
    
	this.width=width;
	this.height=height;
	this.PixImage=new short[width][height][3];
	for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){
			for(int k=0;k<3;k++){
				PixImage[i][j][k]=0; 
			}
		}
	}
  }

  /**
   * getWidth() returns the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
  
    return this.width;
  }

  /**
   * getHeight() returns the height of the image.
   *
   * @return the height of the image.
   */
  public int getHeight() {
  
    return this.height;
  }

  /**
   * getRed() returns the red intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red intensity of the pixel at coordinate (x, y).
   */
  public short getRed(int x, int y) {
 
    return this.PixImage[x][y][0];
  }

  /**
   * getGreen() returns the green intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green intensity of the pixel at coordinate (x, y).
   */
  public short getGreen(int x, int y) {
 
    return this.PixImage[x][y][1];
  }

  /**
   * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue intensity of the pixel at coordinate (x, y).
   */
  public short getBlue(int x, int y) {
  
    return this.PixImage[x][y][2];
  }

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
	  if((red>=0)&&(red<=255)&&(green>=0)&&(green<=255)&&(blue>=0)&&(blue<=255)){
		  this.PixImage[x][y][0]=red;
		  this.PixImage[x][y][1]=green;
		  this.PixImage[x][y][2]=blue;
	  }
  }

  /**
   * toString() returns a String representation of this PixImage.
   *
   * This method isn't required, but it should be very useful to you when
   * you're debugging your code.  It's up to you how you represent a PixImage
   * as a String.
   *
   * @return a String representation of this PixImage.
   */
  public String toString() {
	  String s=new String();
	  int i;
	  for(int j=0;j<this.height;j++){
		  for(i=0;i<this.width;i++ ){
			  for(int k=0;k<3;k++){
				  s=s+String.valueOf(this.PixImage[i][j][k])+",";
				  if(k==2){
					  s=s+"|";
					  if(i==this.width-1){
						  s=s+"\n";
					  }
				  }
				  //s=s+String.valueOf(this.PixImage[i][j][k]);
				  //System.out.println(s+"one time"+"\n");
			  } 
		  }
	  }
	  return s;
  }

  /**
   * boxBlur() returns a blurred version of "this" PixImage.
   *
   * If numIterations == 1, each pixel in the output PixImage is assigned
   * a value equal to the average of its neighboring pixels in "this" PixImage,
   * INCLUDING the pixel itself.
   *
   * A pixel not on the image boundary has nine neighbors--the pixel itself and
   * the eight pixels surrounding it.  A pixel on the boundary has six
   * neighbors if it is not a corner pixel; only four neighbors if it is
   * a corner pixel.  The average of the neighbors is the sum of all the
   * neighbor pixel values (including the pixel itself) divided by the number
   * of neighbors, with non-integer quotients rounded toward zero (as Java does
   * naturally when you divide two integers).
   *
   * Each color (red, green, blue) is blurred separately.  The red input should
   * have NO effect on the green or blue outputs, etc.
   *
   * The parameter numIterations specifies a number of repeated iterations of
   * box blurring to perform.  If numIterations is zero or negative, "this"
   * PixImage is returned (not a copy).  If numIterations is positive, the
   * return value is a newly constructed PixImage.
   *
   * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
   * appear in the new, output PixImage only.
   *
   * @param numIterations the number of iterations of box blurring.
   * @return a blurred version of "this" PixImage.
   */
  public PixImage boxBlur(int numIterations) {    
	if (numIterations<=0)
    return this;
	else{
		//PixImage outPutCurrent=new PixImage(this.width,this.height);
		PixImage outPutCurrent=this;
		//PixImage outPutNext=new PixImage(this.width,this.height);// thinking with the List class 
		//outPutCurrent=this;
		short redReal;
		short greenReal;
		short blueReal;
		////
		PixImage[] aaa=new PixImage[numIterations+1];
		
		for(int ii=0;ii<numIterations;ii++){
			aaa[ii]=new PixImage(this.width,this.height);
		}
		//aaa[0]=new PixImage(this.width,this.height);
		///
		for(int i=0;i<numIterations;i++){
			
			for(int j=0;j<this.width;j++){
				for(int k=0;k<this.height;k++){
					/*
					if(i==1){
						System.out.println("secondTimeDebuggingStarting\n");
						System.out.println(outPutCurrent.toString());
						System.out.println("secondTimeDebuggingEnding\n");
					}
					*/
						//in fact, we should not use 3 but use #define to simplify the code.
						//there should be a category for the neight number 
						/*
						if (((j==0)&&(k==0))
								||((j==0)&&(k==this.height-1))
								||((j==this.width-1)&&(k==0))
								||((j==this.width-1)&&(k==this.height-1))){
								//outPutNext.setPixel(j, k, red, green, blue);						
						}
						*/
						if ((j==0)&&(k==0)){				
							short red=(short) ((outPutCurrent.PixImage[j][k][0]
									+outPutCurrent.PixImage[j+1][k][0]
									+outPutCurrent.PixImage[j][k+1][0]
									+outPutCurrent.PixImage[j+1][k+1][0])/4);
							short green=(short) ((outPutCurrent.PixImage[j][k][1]
									+outPutCurrent.PixImage[j+1][k][1]
									+outPutCurrent.PixImage[j][k+1][1]
									+outPutCurrent.PixImage[j+1][k+1][1])/4);
							short blue=(short) ((outPutCurrent.PixImage[j][k][2]
									+outPutCurrent.PixImage[j+1][k][2]
									+outPutCurrent.PixImage[j][k+1][2]
									+outPutCurrent.PixImage[j+1][k+1][2])/4);
							redReal=red;greenReal=green;blueReal=blue;
						}
						else 
							if((j==0)&&(k==this.height-1)){
								short red=(short) ((outPutCurrent.PixImage[j][k][0]
										+outPutCurrent.PixImage[j+1][k][0]
										+outPutCurrent.PixImage[j][k-1][0]
										+outPutCurrent.PixImage[j+1][k-1][0])/4);
								short green=(short) ((outPutCurrent.PixImage[j][k][1]
										+outPutCurrent.PixImage[j+1][k][1]
										+outPutCurrent.PixImage[j][k-1][1]
										+outPutCurrent.PixImage[j+1][k-1][1])/4);
								short blue=(short) ((outPutCurrent.PixImage[j][k][2]
										+outPutCurrent.PixImage[j+1][k][2]
										+outPutCurrent.PixImage[j][k-1][2]
										+outPutCurrent.PixImage[j+1][k-1][2])/4);redReal=red;greenReal=green;blueReal=blue;
							}
							else
								if((j==this.width-1)&&(k==0)){
									short red=(short) ((outPutCurrent.PixImage[j][k][0]
											+outPutCurrent.PixImage[j-1][k][0]
											+outPutCurrent.PixImage[j][k+1][0]
											+outPutCurrent.PixImage[j-1][k+1][0])/4);
									short green=(short) ((outPutCurrent.PixImage[j][k][1]
											+outPutCurrent.PixImage[j-1][k][1]
											+outPutCurrent.PixImage[j][k+1][1]
											+outPutCurrent.PixImage[j-1][k+1][1])/4);
									short blue=(short) ((outPutCurrent.PixImage[j][k][2]
											+outPutCurrent.PixImage[j-1][k][2]
											+outPutCurrent.PixImage[j][k+1][2]
											+outPutCurrent.PixImage[j-1][k+1][2])/4);redReal=red;greenReal=green;blueReal=blue;
								}
								else
									if((j==this.width-1)&&(k==this.height-1)){
										short red=(short) ((outPutCurrent.PixImage[j][k][0]
												+outPutCurrent.PixImage[j-1][k][0]
												+outPutCurrent.PixImage[j][k-1][0]
												+outPutCurrent.PixImage[j-1][k-1][0])/4);
										short green=(short) ((outPutCurrent.PixImage[j][k][1]
												+outPutCurrent.PixImage[j-1][k][1]
												+outPutCurrent.PixImage[j][k-1][1]
												+outPutCurrent.PixImage[j-1][k-1][1])/4);
										short blue=(short) ((outPutCurrent.PixImage[j][k][2]
												+outPutCurrent.PixImage[j-1][k][2]
												+outPutCurrent.PixImage[j][k-1][2]
												+outPutCurrent.PixImage[j-1][k-1][2])/4);redReal=red;greenReal=green;blueReal=blue;
									}
									else
										if(k==0){
											//divider==6;
											short red=(short) ((outPutCurrent.PixImage[j][k][0]
													+outPutCurrent.PixImage[j-1][k][0]
													+outPutCurrent.PixImage[j+1][k][0]
													+outPutCurrent.PixImage[j][k+1][0]
													+outPutCurrent.PixImage[j-1][k+1][0]
													+outPutCurrent.PixImage[j+1][k+1][0])/6);
											short green=(short) ((outPutCurrent.PixImage[j][k][1]
													+outPutCurrent.PixImage[j-1][k][1]
													+outPutCurrent.PixImage[j+1][k][1]
													+outPutCurrent.PixImage[j][k+1][1]
													+outPutCurrent.PixImage[j-1][k+1][1]
													+outPutCurrent.PixImage[j+1][k+1][1])/6);
											short blue=(short) ((outPutCurrent.PixImage[j][k][2]
													+outPutCurrent.PixImage[j-1][k][2]
													+outPutCurrent.PixImage[j+1][k][2]
													+outPutCurrent.PixImage[j][k+1][2]
													+outPutCurrent.PixImage[j-1][k+1][2]
													+outPutCurrent.PixImage[j+1][k+1][2])/6);redReal=red;greenReal=green;blueReal=blue;
										}
										else
											if(k==this.height-1){
												short red=(short) ((outPutCurrent.PixImage[j][k][0]
														+outPutCurrent.PixImage[j-1][k][0]
														+outPutCurrent.PixImage[j+1][k][0]
														+outPutCurrent.PixImage[j][k-1][0]
														+outPutCurrent.PixImage[j-1][k-1][0]
														+outPutCurrent.PixImage[j+1][k-1][0])/6);
												short green=(short) ((outPutCurrent.PixImage[j][k][1]
														+outPutCurrent.PixImage[j-1][k][1]
														+outPutCurrent.PixImage[j+1][k][1]
														+outPutCurrent.PixImage[j][k-1][1]
														+outPutCurrent.PixImage[j-1][k-1][1]
														+outPutCurrent.PixImage[j+1][k-1][1])/6);
												short blue=(short) ((outPutCurrent.PixImage[j][k][2]
														+outPutCurrent.PixImage[j-1][k][2]
														+outPutCurrent.PixImage[j+1][k][2]
														+outPutCurrent.PixImage[j][k-1][2]
														+outPutCurrent.PixImage[j-1][k-1][2]
														+outPutCurrent.PixImage[j+1][k-1][2])/6);redReal=red;greenReal=green;blueReal=blue;
											}
											else
												if(j==0){
													short red=(short) ((outPutCurrent.PixImage[j][k][0]
															+outPutCurrent.PixImage[j][k-1][0]
															+outPutCurrent.PixImage[j][k+1][0]
															+outPutCurrent.PixImage[j+1][k][0]
															+outPutCurrent.PixImage[j+1][k-1][0]
															+outPutCurrent.PixImage[j+1][k+1][0])/6);
													short green=(short) ((outPutCurrent.PixImage[j][k][1]
															+outPutCurrent.PixImage[j][k-1][1]
															+outPutCurrent.PixImage[j][k+1][1]
															+outPutCurrent.PixImage[j+1][k][1]
															+outPutCurrent.PixImage[j+1][k-1][1]
															+outPutCurrent.PixImage[j+1][k+1][1])/6);
													short blue=(short) ((outPutCurrent.PixImage[j][k][2]
															+outPutCurrent.PixImage[j][k-1][2]
															+outPutCurrent.PixImage[j][k+1][2]
															+outPutCurrent.PixImage[j+1][k][2]
															+outPutCurrent.PixImage[j+1][k-1][2]
															+outPutCurrent.PixImage[j+1][k+1][2])/6);redReal=red;greenReal=green;blueReal=blue;
												}
												else
													if(j==this.width-1){
														short red=(short) ((outPutCurrent.PixImage[j][k][0]
																+outPutCurrent.PixImage[j][k-1][0]
																+outPutCurrent.PixImage[j][k+1][0]
																+outPutCurrent.PixImage[j-1][k][0]
																+outPutCurrent.PixImage[j-1][k-1][0]
																+outPutCurrent.PixImage[j-1][k+1][0])/6);
														short green=(short) ((outPutCurrent.PixImage[j][k][1]
																+outPutCurrent.PixImage[j][k-1][1]
																+outPutCurrent.PixImage[j][k+1][1]
																+outPutCurrent.PixImage[j-1][k][1]
																+outPutCurrent.PixImage[j-1][k-1][1]
																+outPutCurrent.PixImage[j-1][k+1][1])/6);
														short blue=(short) ((outPutCurrent.PixImage[j][k][2]
																+outPutCurrent.PixImage[j][k-1][2]
																+outPutCurrent.PixImage[j][k+1][2]
																+outPutCurrent.PixImage[j-1][k][2]
																+outPutCurrent.PixImage[j-1][k-1][2]
																+outPutCurrent.PixImage[j-1][k+1][2])/6);redReal=red;greenReal=green;blueReal=blue;
													}
													else {// 9 divider
														short red=(short) ((
																outPutCurrent.PixImage[j-1][k-1][0]
																+outPutCurrent.PixImage[j-1][k][0]
																+outPutCurrent.PixImage[j-1][k+1][0]
																+outPutCurrent.PixImage[j][k-1][0]
																+outPutCurrent.PixImage[j][k][0]
																+outPutCurrent.PixImage[j][k+1][0]
																+outPutCurrent.PixImage[j+1][k-1][0]
																+outPutCurrent.PixImage[j+1][k][0]
																+outPutCurrent.PixImage[j+1][k+1][0])/9);
														short green=(short) ((
																outPutCurrent.PixImage[j-1][k-1][1]
																+outPutCurrent.PixImage[j-1][k][1]
																+outPutCurrent.PixImage[j-1][k+1][1]
																+outPutCurrent.PixImage[j][k-1][1]
																+outPutCurrent.PixImage[j][k][1]
																+outPutCurrent.PixImage[j][k+1][1]
																+outPutCurrent.PixImage[j+1][k-1][1]
																+outPutCurrent.PixImage[j+1][k][1]
																+outPutCurrent.PixImage[j+1][k+1][1])/9);
														short blue=(short) ((
																outPutCurrent.PixImage[j-1][k-1][2]
																+outPutCurrent.PixImage[j-1][k][2]
																+outPutCurrent.PixImage[j-1][k+1][2]
																+outPutCurrent.PixImage[j][k-1][2]
																+outPutCurrent.PixImage[j][k][2]
																+outPutCurrent.PixImage[j][k+1][2]
																+outPutCurrent.PixImage[j+1][k-1][2]
																+outPutCurrent.PixImage[j+1][k][2]
																+outPutCurrent.PixImage[j+1][k+1][2])/9);
																redReal=red;greenReal=green;blueReal=blue;
													}
						// outPutCurrent=outPutNext;wrong not simutaneously
						aaa[i].setPixel(j, k, redReal, greenReal, blueReal);						
				}
			}
		outPutCurrent=aaa[i];//Understanding ---sending the pointer so the changing values will bu updated
		//at the same time !!!funny !!!
		//////////Debugging
		/*
		if(i==0){
			System.out.println("after first rep \n");
			System.out.println(outPutCurrent.toString());
			
		}
		else{
			System.out.println("after second rep \n");
			System.out.println(outPutCurrent.toString());
			
		}
		*/
		/////////DebuggingFinish
		}
		return outPutCurrent;
	}
  }

  /**
   * mag2gray() maps an energy (squared vector magnitude) in the range
   * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
   * is logarithmic, but shifted so that values of 5,080 and below map to zero.
   *
   * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
   * correct images and pass the autograder.
   *
   * @param mag the energy (squared vector magnitude) of the pixel whose
   * intensity we want to compute.
   * @return the intensity of the output pixel.
   */
  private static short mag2gray(long mag) {
    short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

    // Make sure the returned intensity is in the range 0...255, regardless of
    // the input value.
    if (intensity < 0) {
      intensity = 0;
    } else if (intensity > 255) {
      intensity = 255;
    }
    return intensity;
  }

  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  public PixImage sobelEdges() {
    
    //return this;
		/*
		if (((j==0)&&(k==0))
				||((j==0)&&(k==this.height-1))
				||((j==this.width-1)&&(k==0))
				||((j==this.width-1)&&(k==this.height-1))){
				//outPutNext.setPixel(j, k, red, green, blue);						
		}
		*/
    short a,b,c,d,e,f,g,h,i;
	PixImage current=new PixImage(this.width,this.height);
	PixImage next=new PixImage(this.width,this.height);// thinking with the List class 
	current=this;
	long energy;
	long gx,gy;
    for(int j=0;j<this.width;j++){
    	for(int k=0;k<this.height;k++){
    		energy=0;
    		for(int l=0;l<3;l++){
        		if ((j==0)&&(k==0)){
        			e=current.PixImage[j][k][l];
        			f=current.PixImage[j+1][k][l];
        			h=current.PixImage[j][k+1][l];
        			i=current.PixImage[j+1][k+1][l];
        			b=e;
        			c=f;
        			d=e;
        			g=h;
        			a=b;
        		}
        		else
        			if((j==0)&&(k==this.height-1)){
        				e=current.PixImage[j][k][l];
        				f=current.PixImage[j+1][k][l];
        				b=current.PixImage[j][k-1][l];
        				c=current.PixImage[j+1][k-1][l];
        				h=e;
        				i=f;
        				a=b;
        				d=e;
        				g=h;	
        			}
        			else
        				if((j==this.width-1)&&(k==0)){
        					e=current.PixImage[j][k][l];
        					d=current.PixImage[j-1][k][l];
        					h=current.PixImage[j][k+1][l];
        					g=current.PixImage[j-1][k+1][l];
        					a=d;
        					b=e;
        					c=b;
        					f=e;
        					i=h;
        				}
        				else
        					if((j==this.width-1)&&(k==this.height-1)){
        						e=current.PixImage[j][k][l];
        						d=current.PixImage[j-1][k][l];
        						b=current.PixImage[j][k-1][l];
        						a=current.PixImage[j-1][k-1][l];
        						g=d;
        						h=e;
        						c=b;
        						f=e;
        						i=h;
        					}
        					else
        						if(j==0){
        							e=current.PixImage[j][k][l];
        							f=current.PixImage[j+1][k][l];
        							b=current.PixImage[j][k-1][l];
        							c=current.PixImage[j+1][k-1][l];
        							h=current.PixImage[j][k+1][l];
        							i=current.PixImage[j+1][k+1][l];
        							a=b;
        							d=e;
        							g=h;
        						}
        						else
        							if(j==this.width-1){
            							e=current.PixImage[j][k][l];
            							a=current.PixImage[j-1][k-1][l];
            							b=current.PixImage[j][k-1][l];
            							d=current.PixImage[j-1][k][l];
            							h=current.PixImage[j][k+1][l];
            							g=current.PixImage[j-1][k+1][l];
            							c=b;
            							f=e;
            							i=h;            							
        							}
        							else
        								if(k==0){
                							e=current.PixImage[j][k][l];
                							d=current.PixImage[j-1][k][l];
                							h=current.PixImage[j][k+1][l];
                							g=current.PixImage[j-1][k+1][l];
                							f=current.PixImage[j+1][k][l];
                							i=current.PixImage[j+1][k+1][l];
                							c=f;
                							a=d;
                							b=e;
        								}
        								else
        									if(k==this.height-1){
                    							e=current.PixImage[j][k][l];
                    							d=current.PixImage[j-1][k][l];
                    							f=current.PixImage[j+1][k][l];
                    							c=current.PixImage[j+1][k-1][l];
                    							a=current.PixImage[j-1][k-1][l];
                    							b=current.PixImage[j][k-1][l];                    							
                    							h=e;
                    							g=d;
                    							i=f;                    							
        									}
        									else{
                    							e=current.PixImage[j][k][l];
                    							d=current.PixImage[j-1][k][l];
                    							h=current.PixImage[j][k+1][l];
                    							g=current.PixImage[j-1][k+1][l];
                    							f=current.PixImage[j+1][k][l];
                    							i=current.PixImage[j+1][k+1][l];
                    							c=current.PixImage[j+1][k-1][l];
                    							a=current.PixImage[j-1][k-1][l];
                    							b=current.PixImage[j][k-1][l];
        									}        		
        		gx=1*a+0*b+(-1)*c+2*d+0*e+(-2)*f+1*g+0*h+(-1)*i;
        		gy=1*a+2*b+1*c+0*d+0*e+0*f+(-1)*g+(-2)*h+(-1)*i;
        		energy=gx*gx+gy*gy+energy;
    		}
    		next.setPixel(j, k, current.mag2gray(energy), current.mag2gray(energy), current.mag2gray(energy));//red=green=blue so set new PixImage
    	}
    }
    return next;
    // Don't forget to use the method mag2gray() above to convert energies to
    // pixel intensities.
  }
 

  
  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  public PixImage PrewittMask() {
    
    //return this;
		/*
		if (((j==0)&&(k==0))
				||((j==0)&&(k==this.height-1))
				||((j==this.width-1)&&(k==0))
				||((j==this.width-1)&&(k==this.height-1))){
				//outPutNext.setPixel(j, k, red, green, blue);						
		}
		*/
    short a,b,c,d,e,f,g,h,i;
	PixImage current=new PixImage(this.width,this.height);
	PixImage next=new PixImage(this.width,this.height);// thinking with the List class 
	current=this;
	long energy;
	long gx,gy;
    for(int j=0;j<this.width;j++){
    	for(int k=0;k<this.height;k++){
    		energy=0;
    		for(int l=0;l<3;l++){
        		if ((j==0)&&(k==0)){
        			e=current.PixImage[j][k][l];
        			f=current.PixImage[j+1][k][l];
        			h=current.PixImage[j][k+1][l];
        			i=current.PixImage[j+1][k+1][l];
        			b=e;
        			c=f;
        			d=e;
        			g=h;
        			a=b;
        		}
        		else
        			if((j==0)&&(k==this.height-1)){
        				e=current.PixImage[j][k][l];
        				f=current.PixImage[j+1][k][l];
        				b=current.PixImage[j][k-1][l];
        				c=current.PixImage[j+1][k-1][l];
        				h=e;
        				i=f;
        				a=b;
        				d=e;
        				g=h;	
        			}
        			else
        				if((j==this.width-1)&&(k==0)){
        					e=current.PixImage[j][k][l];
        					d=current.PixImage[j-1][k][l];
        					h=current.PixImage[j][k+1][l];
        					g=current.PixImage[j-1][k+1][l];
        					a=d;
        					b=e;
        					c=b;
        					f=e;
        					i=h;
        				}
        				else
        					if((j==this.width-1)&&(k==this.height-1)){
        						e=current.PixImage[j][k][l];
        						d=current.PixImage[j-1][k][l];
        						b=current.PixImage[j][k-1][l];
        						a=current.PixImage[j-1][k-1][l];
        						g=d;
        						h=e;
        						c=b;
        						f=e;
        						i=h;
        					}
        					else
        						if(j==0){
        							e=current.PixImage[j][k][l];
        							f=current.PixImage[j+1][k][l];
        							b=current.PixImage[j][k-1][l];
        							c=current.PixImage[j+1][k-1][l];
        							h=current.PixImage[j][k+1][l];
        							i=current.PixImage[j+1][k+1][l];
        							a=b;
        							d=e;
        							g=h;
        						}
        						else
        							if(j==this.width-1){
            							e=current.PixImage[j][k][l];
            							a=current.PixImage[j-1][k-1][l];
            							b=current.PixImage[j][k-1][l];
            							d=current.PixImage[j-1][k][l];
            							h=current.PixImage[j][k+1][l];
            							g=current.PixImage[j-1][k+1][l];
            							c=b;
            							f=e;
            							i=h;            							
        							}
        							else
        								if(k==0){
                							e=current.PixImage[j][k][l];
                							d=current.PixImage[j-1][k][l];
                							h=current.PixImage[j][k+1][l];
                							g=current.PixImage[j-1][k+1][l];
                							f=current.PixImage[j+1][k][l];
                							i=current.PixImage[j+1][k+1][l];
                							c=f;
                							a=d;
                							b=e;
        								}
        								else
        									if(k==this.height-1){
                    							e=current.PixImage[j][k][l];
                    							d=current.PixImage[j-1][k][l];
                    							f=current.PixImage[j+1][k][l];
                    							c=current.PixImage[j+1][k-1][l];
                    							a=current.PixImage[j-1][k-1][l];
                    							b=current.PixImage[j][k-1][l];                    							
                    							h=e;
                    							g=d;
                    							i=f;                    							
        									}
        									else{
                    							e=current.PixImage[j][k][l];
                    							d=current.PixImage[j-1][k][l];
                    							h=current.PixImage[j][k+1][l];
                    							g=current.PixImage[j-1][k+1][l];
                    							f=current.PixImage[j+1][k][l];
                    							i=current.PixImage[j+1][k+1][l];
                    							c=current.PixImage[j+1][k-1][l];
                    							a=current.PixImage[j-1][k-1][l];
                    							b=current.PixImage[j][k-1][l];
        									}        		
        		gx=-1*a+0*b+(1)*c-1*d+0*e+(+1)*f-1*g+0*h+(+1)*i;
        		gy=1*a+1*b+1*c+0*d+0*e+0*f+(-1)*g+(-1)*h+(-1)*i;
        		energy=gx*gx+gy*gy+energy;
    		}
    		next.setPixel(j, k, current.mag2gray(energy), current.mag2gray(energy), current.mag2gray(energy));//red=green=blue so set new PixImage
    	}
    }
    return next;
    // Don't forget to use the method mag2gray() above to convert energies to
    // pixel intensities.
  }
  
  /**
   * 1_d derevertive edge detection
   */
  
  
  
  public PixImage oneDeriDetec(){
		PixImage current=new PixImage(this.width,this.height);
		PixImage next=new PixImage(this.width,this.height);// thinking with the List class 
		current=this;
		int a,b;
		int red,green,blue;
		red=0;
		green=0;
		blue=0;
		for(int i=0;i<this.width;i++){
			for(int j=0;j<this.height;j++){
				for(int k=0;k<3;k++){
					if(i==(this.width-1)){
						a=current.PixImage[i][j][k];
						b=current.PixImage[i][j][k];
					}
					else{
						a=current.PixImage[i][j][k];
						b=current.PixImage[i+1][j][k];
					}
					//compute r,g,b
					if(k==0){
						red=-a+b;
					}
					else
						if(k==1){
							green=-a+b;
						}
						else{
							blue=-a+b;
						}
				}
				next.setPixel(i,j,(short) red,(short) green,(short) blue);
			}
		}
	  return next;
  }



  /*
   * Gaussin filter with 9 boxes:
   */
  
  public PixImage GaussianFilter(int numIterations) {
	   
	    //return this;
			/*
			if (((j==0)&&(k==0))
					||((j==0)&&(k==this.height-1))
					||((j==this.width-1)&&(k==0))
					||((j==this.width-1)&&(k==this.height-1))){
					//outPutNext.setPixel(j, k, red, green, blue);						
			}
			*/
	    if (numIterations<=0)
		    return this;
	  	
		short redReal=0;
		short greenReal=0;
		short blueReal=0;
	    short a,b,c,d,e,f,g,h,i;
	    
	    PixImage[] aaa=new PixImage[numIterations+1];		
		for(int ii=0;ii<numIterations;ii++){
			aaa[ii]=new PixImage(this.width,this.height);
		}
		
		PixImage current=new PixImage(this.width,this.height);
		
		current=this;
		//long energy;
		//long gx,gy;
		//PixImage next=new PixImage(this.width,this.height);// thinking with the List class 
		
		//PixImage outPutCurrent=this;
		for(int ii=0;ii<numIterations;ii++){
	     for(int j=0;j<this.width;j++){
	    	for(int k=0;k<this.height;k++){
	    		//energy=0;
	    		for(int l=0;l<3;l++){
	        		if ((j==0)&&(k==0)){
	        			e=current.PixImage[j][k][l];
	        			f=current.PixImage[j+1][k][l];
	        			h=current.PixImage[j][k+1][l];
	        			i=current.PixImage[j+1][k+1][l];
	        			b=e;
	        			c=f;
	        			d=e;
	        			g=h;
	        			a=b;
	        		}
	        		else
	        			if((j==0)&&(k==this.height-1)){
	        				e=current.PixImage[j][k][l];
	        				f=current.PixImage[j+1][k][l];
	        				b=current.PixImage[j][k-1][l];
	        				c=current.PixImage[j+1][k-1][l];
	        				h=e;
	        				i=f;
	        				a=b;
	        				d=e;
	        				g=h;	
	        			}
	        			else
	        				if((j==this.width-1)&&(k==0)){
	        					e=current.PixImage[j][k][l];
	        					d=current.PixImage[j-1][k][l];
	        					h=current.PixImage[j][k+1][l];
	        					g=current.PixImage[j-1][k+1][l];
	        					a=d;
	        					b=e;
	        					c=b;
	        					f=e;
	        					i=h;
	        				}
	        				else
	        					if((j==this.width-1)&&(k==this.height-1)){
	        						e=current.PixImage[j][k][l];
	        						d=current.PixImage[j-1][k][l];
	        						b=current.PixImage[j][k-1][l];
	        						a=current.PixImage[j-1][k-1][l];
	        						g=d;
	        						h=e;
	        						c=b;
	        						f=e;
	        						i=h;
	        					}
	        					else
	        						if(j==0){
	        							e=current.PixImage[j][k][l];
	        							f=current.PixImage[j+1][k][l];
	        							b=current.PixImage[j][k-1][l];
	        							c=current.PixImage[j+1][k-1][l];
	        							h=current.PixImage[j][k+1][l];
	        							i=current.PixImage[j+1][k+1][l];
	        							a=b;
	        							d=e;
	        							g=h;
	        						}
	        						else
	        							if(j==this.width-1){
	            							e=current.PixImage[j][k][l];
	            							a=current.PixImage[j-1][k-1][l];
	            							b=current.PixImage[j][k-1][l];
	            							d=current.PixImage[j-1][k][l];
	            							h=current.PixImage[j][k+1][l];
	            							g=current.PixImage[j-1][k+1][l];
	            							c=b;
	            							f=e;
	            							i=h;            							
	        							}
	        							else
	        								if(k==0){
	                							e=current.PixImage[j][k][l];
	                							d=current.PixImage[j-1][k][l];
	                							h=current.PixImage[j][k+1][l];
	                							g=current.PixImage[j-1][k+1][l];
	                							f=current.PixImage[j+1][k][l];
	                							i=current.PixImage[j+1][k+1][l];
	                							c=f;
	                							a=d;
	                							b=e;
	        								}
	        								else
	        									if(k==this.height-1){
	                    							e=current.PixImage[j][k][l];
	                    							d=current.PixImage[j-1][k][l];
	                    							f=current.PixImage[j+1][k][l];
	                    							c=current.PixImage[j+1][k-1][l];
	                    							a=current.PixImage[j-1][k-1][l];
	                    							b=current.PixImage[j][k-1][l];                    							
	                    							h=e;
	                    							g=d;
	                    							i=f;                    							
	        									}
	        									else{
	                    							e=current.PixImage[j][k][l];
	                    							d=current.PixImage[j-1][k][l];
	                    							h=current.PixImage[j][k+1][l];
	                    							g=current.PixImage[j-1][k+1][l];
	                    							f=current.PixImage[j+1][k][l];
	                    							i=current.PixImage[j+1][k+1][l];
	                    							c=current.PixImage[j+1][k-1][l];
	                    							a=current.PixImage[j-1][k-1][l];
	                    							b=current.PixImage[j][k-1][l];
	        									}
	        		
	        		// compute the gaussian filter
	        		if(l==0){
	        			redReal=(short) ((a+c+g+i+2*b+2*d+2*f+2*h+3*e)/15);
	        		}
	        		else
	        			if(l==1){
	        				greenReal=(short) ((a+c+g+i+2*b+2*d+2*f+2*h+3*e)/15);
	        			}
	        			else{
	        				blueReal=(short) ((a+c+g+i+2*b+2*d+2*f+2*h+3*e)/15);
	        			}   		
	    		}
	    		
	    		aaa[ii].setPixel(j, k, redReal, greenReal, blueReal);
	    		//next.setPixel(j, k, current.mag2gray(energy), current.mag2gray(energy), current.mag2gray(energy));//red=green=blue so set new PixImage
	    	}
	    }
	    current=aaa[ii];
		}
	    // Don't forget to use the method mag2gray() above to convert energies to
	    // pixel intensities.
	return current;  
  }
  
  
  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  public static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * equals() checks whether two images are the same, i.e. have the same
   * dimensions and pixels.
   *
   * @param image a PixImage to compare with "this" PixImage.
   * @return true if the specified PixImage is identical to "this" PixImage.
   */
  public boolean equals(PixImage image) {
    int width = getWidth();
    int height = getHeight();

    if (image == null ||
        width != image.getWidth() || height != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (! (getRed(x, y) == image.getRed(x, y) &&
               getGreen(x, y) == image.getGreen(x, y) &&
               getBlue(x, y) == image.getBlue(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * main() runs a series of tests to ensure that the convolutions (box blur
   * and Sobel) are correct.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 },
                                                   { 30, 120, 250 },
                                                   { 80, 250, 255 } });
    System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                       "Input image:");
    System.out.print(image1);
    doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 3x3 image.");
    doTest(image1.boxBlur(1).equals(
           array2PixImage(new int[][] { { 40, 108, 155 },
                                        { 81, 137, 187 },
                                        { 120, 164, 218 } })),
           "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
    doTest(image1.boxBlur(2).equals(
           array2PixImage(new int[][] { { 91, 118, 146 },
                                        { 108, 134, 161 },
                                        { 125, 151, 176 } })),
           "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
    doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
           "Incorrect box blur (1 rep + 1 rep):\n" +
           image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

    System.out.println("Testing edge detection on a 3x3 image.");
    doTest(image1.sobelEdges().equals(
           array2PixImage(new int[][] { { 104, 189, 180 },
                                        { 160, 193, 157 },
                                        { 166, 178, 96 } })),
           "Incorrect Sobel:\n" + image1.sobelEdges());


    PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                                                   { 0, 0, 100 } });
    System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                       "Input image:");
    System.out.print(image2);
    doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 2x3 image.");
    doTest(image2.boxBlur(1).equals(
           array2PixImage(new int[][] { { 25, 50, 75 },
                                        { 25, 50, 75 } })),
           "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

    System.out.println("Testing edge detection on a 2x3 image.");
    doTest(image2.sobelEdges().equals(
           array2PixImage(new int[][] { { 122, 143, 74 }, 
                                        { 74, 143, 122 } })),
           "Incorrect Sobel:\n" + image2.sobelEdges());
  }
}
