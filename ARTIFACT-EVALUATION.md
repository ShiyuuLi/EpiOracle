# Artifact Appendix
## Basic Requirements
### Hardware Requirements
No specific hardware is required. The experiments were conducted on a laptop with an Intel Core i5 CPU and 16 GB of LPDDR4X RAM.

### Software Requirements
- Packages. All required packages are contained in `./EpiOracle/lib`.
- Database. We use MySQL 9.0 as the underlying database.  You can download MySQL Community Server [here](https://dev.mysql.com/downloads/mysql/).

## Artifact Evaluation
### Main Results and Claims
#### Main Result 1: Accuracy of fuzzy detection
In Section 9.3 of the paper, Figure 7 presents the accuracy of fuzzy detection. The experiments show:

- The number of symptom lists identified as similar using only COVID-19 symptom lists.
- The number of similar symptom lists when both COVID-19 symptom lists and "noisy" lists (containing other symptoms) are used.

These results can be obtained by running **Experiment 1 & 2**, respectively. However, the experiment results may vary since fuzzy detection is a randomized algorithm.

#### Main Result 2: Accuracy of increment count
In Section 9.3, Figure 8 shows the accuracy of increment count in a Bloom Filter. After inserting a target tag $t$ times and other tags $\iota$ times, we measure the recovered count of the target tag. 

The results can be obtained by running **Experiment 3**. Due to the false positive nature of Bloom Filter, the results should be close to $T$.

#### Main Result 3: Computational delay 

In Section 9.4, Figure 9 presents the computation delay on the healthcare facility side under different configurations. 

The results can be reproduced by running **Experiment 1** with the following configuration

- in `EpiOracle/src/main/java/Main.java`

  - set $L = 2^{12}, s = 2^{11}, l = 1/5/10$

  - Comment out line 20

  - Uncomment line 18 and set $alpha = 16/18/20/22/24/26$

- in `EpiOracle/src/main/java/HelParGen.java`

  - Comment out line 137 and uncomment line 136

### Experiments 
#### Experiment 1: Accuracy of fuzzy detection without noise
Run `EpiOracle/src/main/java/Main.java`, where the parameter $Sym\_Num$ should be set to $400/800/1200/1600/2000$.

#### Experiment 2: Accuracy of fuzzy detection with noise
Run `EpiOracle/src/main/java/Main.java`, where the following parameters should be reset:

-  $Sym\_{Num} = 2000$
- $SimRatio = 0.2/0.4/0.6/0.8$
- $Noi\_{Num} = 0/2000$

#### Experiment 3: Accuracy of increment count 
Run `EpiOracle/src/main/java/utils/BloomFilter.java` with the following parameter configurations:

- $t = 300/600/900/1200$
- $L = 2^{12}/2^{14}/2^{16}$
- $s = 2^{11}/2^{12}$

## Limitations
The experimental results of fuzzy detection based on 200 real-world symptom lists are not reproducible, which are presented in Table 3 of the paper.

The reason for this non-reproducibility is that we do not publish the real-world dataset used in the experiments for privacy.
