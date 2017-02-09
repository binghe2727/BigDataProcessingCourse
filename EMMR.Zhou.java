import java.io.*;
import java.math.*;
import java.util.*;

public class EM {
	// Labels from worker to object
	public static int[][] labels;
	// number of objects
	public static int N;
	// number of workers
	public static int K;
	// number of labels
	public static int L;
	public static int[] likertScaleLabels;
	public static HashMap<Integer, Integer> labelIndexMaping = new HashMap<Integer, Integer>();
	public static int[] trueLabelOfObjs;
	public static BigDecimal[][] objsLabelScores;

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("EM begin...");
		initParameters();
		// initTestCasePornParameters();// cs
		// initTestCaseInclass04Parameters();// cs
		// init worker's error rate : all correct;
		ArrayList<BigDecimal[][]> errorRatesList = initErrorRates(K, L);
		// init correct label for each object with majority vote
		trueLabelOfObjs = calculateCorrectLabel(labels, errorRatesList, likertScaleLabels, objsLabelScores, N, K, L);
		int[] lastTrueLabelOfObjs = new int[trueLabelOfObjs.length];
		int iterationNum = 0;
		while (iterationNum == 0 | !hasConverged(trueLabelOfObjs, lastTrueLabelOfObjs)) {
			// set last true label
			
			
			
			lastTrueLabelOfObjs = trueLabelOfObjs;
			// update worker's error rates
			errorRatesList = updateWorkerErrorRates(labels, objsLabelScores, K, L, N);
			errorRatesList = calculateWorkersAccuracy(errorRatesList);
			// update estimates for true labels;
			trueLabelOfObjs = calculateCorrectLabel(labels, errorRatesList, likertScaleLabels, objsLabelScores, N, K,
					L);
			iterationNum++;


		}
		errorRatesList = calculateWorkersAccuracy(errorRatesList);
		exportResults(iterationNum, errorRatesList, trueLabelOfObjs, likertScaleLabels);
		System.out.println("Success.");
	}

	/**
	 * init parameters
	 * 
	 * @throws FileNotFoundException
	 */
	public static void initParameters() throws FileNotFoundException {
		N = 2000;
		K = 500;
		L = 5;
		likertScaleLabels = new int[L];
		// { -2, -1, 0, 1, 2 };
		likertScaleLabels[0] = -2;
		likertScaleLabels[1] = -1;
		likertScaleLabels[2] = 0;
		likertScaleLabels[3] = 1;
		likertScaleLabels[4] = 2;

		for (int i = 0; i < likertScaleLabels.length; i++) {
			labelIndexMaping.put(likertScaleLabels[i], i);
		}
		trueLabelOfObjs = new int[N];
		objsLabelScores = new BigDecimal[N][L];
		labels = new int[N][K];
		Scanner sc = new Scanner(new File("score.txt"));
		for (int j = 0; j < K; j++) {
			for (int i = 0; i < N; i++) {
				labels[i][j] = sc.nextInt();
			}
		}
		sc.close();
	}

	public static int[] calculateCorrectLabel(int[][] labels, ArrayList<BigDecimal[][]> errorRatesList,
			int[] LikertScaleLabels, BigDecimal[][] objsLabelScores, int numbOfObjs, int numbOfWorkers,
			int numbOfLabels) {
		int[] trueLabelOfObjs = new int[numbOfObjs];
		Random r = new Random();
		for (int i = 0; i < numbOfObjs; i++) {
			BigDecimal[] curObjLabelScores = objsLabelScores[i];
			// calculate score for each label of current obj
			for (int j = 0; j < numbOfWorkers; j++) {
				// get the label from current worker for current obj
				int label = labels[i][j];
				// get the index of the label in Likert Scale
				int index = labelIndexMaping.get(label);
				// get current worker's error rates matrix
				BigDecimal[][] errorRateMatrix = errorRatesList.get(j);
				// update the score for each label of current obj from current
				// worker
				for (int k = 0; k < numbOfLabels; k++) {
					BigDecimal score = curObjLabelScores[k] == null ? BigDecimal.ZERO : curObjLabelScores[k];
					BigDecimal errorRate = errorRateMatrix[k][index] == null ? BigDecimal.ZERO
							: errorRateMatrix[k][index];
					curObjLabelScores[k] = score.add(errorRate);
				}
			}

			// majority vote
			double max = 0.0;
			int majorityLabelIndex = -1;
			for (int j = 0; j < curObjLabelScores.length; j++) {
				double curLabelScore = curObjLabelScores[j].doubleValue();
				if (curLabelScore > max) {
					max = curLabelScore;
					majorityLabelIndex = j;
				} else if (curLabelScore == max) {
					// randomly replacing
					if (r.nextInt(10) >= 4) {
						max = curLabelScore;
						majorityLabelIndex = j;
					}
				}
			}
			// get the majority label
			int trueLabel = likertScaleLabels[majorityLabelIndex];
			trueLabelOfObjs[i] = trueLabel;
			// reset all Label Scores of current object
			// keep the majority to be 100%, others be 0%
			for (int j = 0; j < curObjLabelScores.length; j++) {
				if (j != majorityLabelIndex) {
					curObjLabelScores[j] = BigDecimal.ZERO;
				} else {
					curObjLabelScores[j] = BigDecimal.ONE;
				}
			}
		}
		return trueLabelOfObjs;
	}

	/**
	 * update worker error rates value to the true number not percentage
	 * 
	 * @param labels
	 * @param objsLabelScores
	 * @param numOfWorkers
	 * @param numOflables
	 * @param numOfObjs
	 * @return
	 */
	public static ArrayList<BigDecimal[][]> updateWorkerErrorRates(int[][] labels, BigDecimal[][] objsLabelScores,
			int numOfWorkers, int numOflables, int numOfObjs) {
		ArrayList<BigDecimal[][]> newErrorRateList = new ArrayList<>();
		for (int i = 0; i < numOfWorkers; i++) {
			BigDecimal[][] errorRateMatrix = new BigDecimal[numOflables][numOflables];
			for (int j = 0; j < numOfObjs; j++) {
				// get the label from current worker to current object
				int label = labels[j][i];
				int indexOfLabel = labelIndexMaping.get(label);
				for (int k = 0; k < numOflables; k++) {
					// objsLabelScores[j][k] could only be 0 or 1;
					// find the true label of current object and update the
					// errorRateMatrix of current worker
					if (objsLabelScores[j][k].compareTo(BigDecimal.ONE) == 0) {
						BigDecimal errorRate = errorRateMatrix[k][indexOfLabel] == null ? BigDecimal.ZERO
								: errorRateMatrix[k][indexOfLabel];
						errorRateMatrix[k][indexOfLabel] = errorRate.add(objsLabelScores[j][k]);
						continue;
					}
				}
			}
			newErrorRateList.add(errorRateMatrix);
		}
		return newErrorRateList;
	}

	/**
	 * calculate worker error rates to percentage
	 * 
	 * @param errorRatesList
	 * @return
	 */
	public static ArrayList<BigDecimal[][]> calculateWorkersAccuracy(ArrayList<BigDecimal[][]> errorRatesList) {
		for (int i = 0; i < errorRatesList.size(); i++) {
			BigDecimal[][] workerErrorRatesMatrix = errorRatesList.get(i);
			for (int j = 0; j < workerErrorRatesMatrix.length; j++) {
				BigDecimal[] errorRateArr = workerErrorRatesMatrix[j];
				BigDecimal total = BigDecimal.ZERO;
				for (BigDecimal errorRate : errorRateArr) {
					if (errorRate == null) {
						errorRate = BigDecimal.ZERO;
					}
					total = total.add(errorRate);
				}
				if (total.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				for (int k = 0; k < errorRateArr.length; k++) {
					if (errorRateArr[k] == null) {
						errorRateArr[k] = BigDecimal.ZERO;
					}
					// update true value to percentage
					errorRateArr[k] = errorRateArr[k].divide(total, 2, BigDecimal.ROUND_HALF_UP);
				}
			}
		}
		return errorRatesList;
	}

	/**
	 * consider two iterations output the same result as converged
	 * 
	 * @param trueLabelOfObjs
	 * @param lastTrueLabelOfObjs
	 * @return
	 */
	public static boolean hasConverged(int[] trueLabelOfObjs, int[] lastTrueLabelOfObjs) {
		for (int i = 0; i < trueLabelOfObjs.length; i++) {
			if (trueLabelOfObjs[i] != lastTrueLabelOfObjs[i]) {
				return false;
			}
		}
		return true;
	}

	public static void exportResults(int iterationNum, ArrayList<BigDecimal[][]> errorRatesList, int[] trueLabelOfObjs,
			int[] likertScaleLabels) {

		String fileName = "a2#MB654703.csv";
		File csvFile = new File(fileName);
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"),
					1024);
			csvFileOutputStream.write("Converge iterations:," + iterationNum);
			csvFileOutputStream.newLine();
			csvFileOutputStream.newLine();
			csvFileOutputStream.write("\"True\" label of items:");
			csvFileOutputStream.newLine();
			csvFileOutputStream.write("item_id,True Label");
			csvFileOutputStream.newLine();
			for (int i = 0; i < trueLabelOfObjs.length; i++) {
				csvFileOutputStream.write(i + "," + trueLabelOfObjs[i]);
				csvFileOutputStream.newLine();
			}
			csvFileOutputStream.newLine();
			csvFileOutputStream.write("Worker accuracy:");
			csvFileOutputStream.newLine();
			for (int j = 0; j < errorRatesList.size(); j++) {
				csvFileOutputStream.write(j + ",");
				for (int i = 0; i < likertScaleLabels.length; i++) {
					csvFileOutputStream.write(likertScaleLabels[i] + ",");
				}
				csvFileOutputStream.newLine();
				BigDecimal[][] metrix = errorRatesList.get(j);
				for (int i = 0; i < metrix.length; i++) {
					csvFileOutputStream.write(likertScaleLabels[i] + ",");
					for (BigDecimal accuracy : metrix[i]) {
						if (accuracy == null) {
							accuracy = BigDecimal.ZERO;
						}
						accuracy = accuracy.multiply(new BigDecimal("100"));
						accuracy = accuracy.setScale(0, BigDecimal.ROUND_HALF_UP);
						csvFileOutputStream.write(accuracy + "%" + ",");
					}
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<BigDecimal[][]> initErrorRates(int numOfWorkers, int numOflables) {
		ArrayList<BigDecimal[][]> errorRatesList = new ArrayList<BigDecimal[][]>();
		for (int i = 0; i < numOfWorkers; i++) {
			BigDecimal[][] errorRateMatrix = new BigDecimal[numOflables][numOflables];
			for (int j = 0; j < numOflables; j++) {
				for (int k = 0; k < numOflables; k++) {
					if (j == k) {
						errorRateMatrix[j][k] = new BigDecimal("1.0");
					}
				}
			}
			errorRatesList.add(errorRateMatrix);
		}
		return errorRatesList;
	}

	/** ================== debug function ================== */
	public static void printlnBigArr(BigDecimal[][] bigArrArr) {
		for (BigDecimal[] arr : bigArrArr) {
			for (BigDecimal bd : arr) {
				System.out.print(bd + "\t");
			}
			System.out.println();
		}
	}// cs

	public static void printlnTrueLabel(int[] trueLabelOfObjs) {
		System.out.println("trueLabelOfObjs======");// cs
		for (int in : trueLabelOfObjs) {
			System.out.println(in);
		}
	}// cs

	public static void printlnErrorRates(ArrayList<BigDecimal[][]> errorRatesList) {
		for (int i = 0; i < errorRatesList.size(); i++) {
			System.out.println("绗�" + i + "th 宸ヤ汉鐨� error rate matrix:");
			printlnBigArr(errorRatesList.get(i));
		} // cs
	}

	public static void initTestCaseInclass04Parameters() throws FileNotFoundException {
		N = 4;
		K = 4;
		L = 3;
		likertScaleLabels = new int[L];
		// { 0, 1, 2 }; 0-bad;1-neutral;2-good;
		likertScaleLabels[0] = 0;
		likertScaleLabels[1] = 1;
		likertScaleLabels[2] = 2;

		for (int i = 0; i < likertScaleLabels.length; i++) {
			labelIndexMaping.put(likertScaleLabels[i], i);
		}
		trueLabelOfObjs = new int[N];
		objsLabelScores = new BigDecimal[N][L];
		labels = new int[N][K];
		Scanner sc = new Scanner(new File("scoreInclass04.txt"));
		for (int j = 0; j < K; j++) {
			for (int i = 0; i < N; i++) {
				labels[i][j] = sc.nextInt();
			}
		}
		sc.close();
	}

	/**
	 * init testCase parameters
	 */
	public static void initTestCasePornParameters() {
		int porn = 1;
		int not = 0;
		labels = new int[5][5];// cs
		int[] o1 = { porn, not, not, not, porn };
		int[] o2 = { porn, porn, porn, porn, porn };
		int[] o3 = { not, porn, not, not, porn };
		int[] o4 = { porn, porn, porn, porn, not };
		int[] o5 = { porn, not, not, not, porn };
		labels[0] = o1;
		labels[1] = o2;
		labels[2] = o3;
		labels[3] = o4;
		labels[4] = o5;
		N = 5;
		K = 5;
		L = 2;
		likertScaleLabels = new int[L];
		// { porn,not };
		likertScaleLabels[0] = porn;
		likertScaleLabels[1] = not;
		for (int i = 0; i < likertScaleLabels.length; i++) {
			labelIndexMaping.put(likertScaleLabels[i], i);
		}
		trueLabelOfObjs = new int[N];
		objsLabelScores = new BigDecimal[N][L];
	}
}