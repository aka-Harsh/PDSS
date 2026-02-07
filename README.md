# PDSS Project: Distributed Sparse Linear Algebra Engine

A high-performance distributed engine for large-scale sparse matrix and tensor operations built on Apache Spark. This system implements five core sparse operations (SpMV-Dense, SpMV-Sparse, SpMM-Dense, SpMM-Sparse, and MTTKRP) with advanced optimizations including CSR/CSF format conversion, broadcast variables, co-partitioning, and algebraic optimizations. Achieves 230× speedup over raw baseline through hybrid data layout strategies.

---

## 🚀 How to Run

### Prerequisites
- Scala 2.12.x
- Apache Spark 3.x
- SBT (Scala Build Tool)
- Java 8 or higher

### Compilation
```bash
# Clean and compile the project
sbt clean compile
```
**What it does:** Cleans previous build artifacts and compiles all Scala source files, checking for syntax errors and type mismatches.

---

### Running Benchmarks

#### 1. Raw Baseline Benchmarks (No Optimizations)
```bash
sbt "runMain BenchmarkRunner raw"
```
**What it does:** Runs all 5 operations (SpMV-Dense, SpMV-Sparse, SpMM-Dense, SpMM-Sparse, MTTKRP) using pure COO format with no broadcast variables, no format conversion, and no co-partitioning. Provides baseline performance metrics to compare against optimized implementations.

---

#### 2. Microbenchmarks (SpMV Comparison)
```bash
sbt "runMain BenchmarkRunner microbench"
```
**What it does:** Compares SpMV implementations across different formats and frameworks:
- Your RDD (COO format)
- Your RDD (CSR format)
- Spark DataFrame
- MLlib IndexedRowMatrix

---

#### 3. SpMM Sparse Benchmarks
```bash
sbt "runMain BenchmarkRunner spmmsparse"
```
**What it does:** Tests Sparse Matrix × Sparse Matrix multiplication with three implementations:
- Your RDD (COO basic join)
- Your RDD (COO with co-partitioning optimization)
- Spark DataFrame

---

#### 4. SpMM Optimized (CSR×CSC)
```bash
sbt "runMain BenchmarkRunner spmmopt"
```
**What it does:** Runs the optimized SpMM implementation using CSR×CSC format with merge-based intersection algorithm. Demonstrates advanced format optimization for sparse-sparse matrix multiplication.

---

#### 5. MTTKRP Tensor Benchmarks
```bash
sbt "runMain BenchmarkRunner mttkrp"
```
**What it does:** Evaluates Matricized Tensor Times Khatri-Rao Product (MTTKRP) with multiple implementations:
- COO format (distributed RDD)
- COO format (local sequential)
- CSF format (local with cache optimization)
- Spark DataFrame

---

#### 6. Scalability Tests
```bash
sbt "runMain BenchmarkRunner scalability"
```
**What it does:** Tests how the system scales with different numbers of CPU cores (1, 2, 4, 8 cores). Creates multiple SparkContexts sequentially to measure speedup, efficiency, and parallel scaling characteristics.


---

#### 7. Ablation Study
```bash
sbt "runMain BenchmarkRunner ablation"
```
**What it does:** Systematically enables/disables individual optimizations to measure their isolated impact:
- Baseline (COO, no optimizations)
- + Custom Row Partitioner
- + Caching
- + Broadcast Variables
- + CSR Format
- All Optimizations Combined


---

#### 8. Algebraic Optimizations Demo
```bash
sbt "runMain BenchmarkRunner algebraic"
```
**What it does:** Demonstrates three algebraic optimization techniques:
- Early zero filtering (skip empty rows/columns)
- Symbolic preprocessing (analyze sparsity patterns)
- Operation fusion (combine multiple operations)

---

#### 9. Matrix Factorization (Recommender System)
```bash
sbt "runMain MatrixFactorizationDemo"
```
**What it does:** Runs Alternating Least Squares (ALS) matrix factorization for a recommendation system. Trains on 1000 users × 500 items with 5000 ratings (1% density) for 10 iterations.


---

#### 10. Run All Benchmarks
```bash
sbt "runMain BenchmarkRunner all"
```
**What it does:** Executes the complete benchmark suite (all outputs in one cmd).


---

## 📁 Project Structure (Core)
```
PDSS-Prototype/
├── src/main/scala/
│   ├── BenchmarkRunner.scala           # Main benchmark orchestrator
│   ├── MatrixFactorizationDemo.scala   # ALS recommender system demo
│   │
│   ├── common/                          # Shared data types and utilities
│   │   ├── COOEntry.scala              # Coordinate format entry
│   │   ├── DataLoaders.scala           # MTX file loading utilities
│   │   └── SparseVectorEntry.scala     # Sparse vector representation
│   │
│   ├── operations/                      # Core sparse operations
│   │   ├── SpMVDense.scala             # Sparse Matrix × Dense Vector
│   │   ├── SpMVSparse.scala            # Sparse Matrix × Sparse Vector
│   │   ├── SpMMDense.scala             # Sparse Matrix × Dense Matrix
│   │   ├── SpMMSparse.scala            # Sparse Matrix × Sparse Matrix (basic)
│   │   ├── SpMMSparseOptimized.scala   # SpMM with co-partitioning
│   │   ├── SpMMDenseCSR.scala          # SpMM-Dense using CSR format
│   │   └── RawBaseline.scala           # Unoptimized baseline implementations
│   │
│   ├── adv_data_layouts/                # Advanced format implementations
│   │   ├── CSRFormat.scala             # Compressed Sparse Row format
│   │   ├── CSCFormat.scala             # Compressed Sparse Column format
│   │   ├── SpMVCSR.scala               # CSR-based SpMV operations
│   │   └── SpMMSparseOptimized.scala   # CSR×CSC multiplication
│   │
│   ├── tensor/                          # Tensor operations
│   │   ├── TensorFormats.scala         # COO/CSF tensor formats
│   │   ├── MTTKRP.scala                # MTTKRP implementations (COO)
│   │   └── MTTKRPCSF.scala             # CSF-based MTTKRP
│   │
│   ├── adv_optimizations/               # Algebraic optimizations
│   │   └── AlgebraicOptimizations.scala # Zero filtering, fusion, preprocessing
│   │
│   ├── benchmarks/                      # Benchmark suites
│   │   ├── PerformanceBenchmark.scala  # SpMV microbenchmarks
│   │   ├── SpMMSparseBenchmark.scala   # SpMM-Sparse benchmarks
│   │   ├── MTTKRPBenchmark.scala       # Tensor benchmarks
│   │   ├── ScalabilityTest.scala       # Multi-core scaling tests
│   │   ├── AblationStudy.scala         # Optimization impact analysis
│   │   └── PageRankDemoSimple.scala    # End-to-end PageRank demo
│   │
│   └── small/                           # Small matrix benchmarks (200×200)
│       ├── SpMVDenseSmall.scala        # 200×200 SpMV-Dense benchmark
│       ├── SpMVSparseSmall.scala       # 200×200 SpMV-Sparse benchmark
│       ├── SpMMDenseSmall.scala        # 200×200 SpMM-Dense benchmark
│       ├── SpMMSparseSmall.scala       # 200×200 SpMM-Sparse benchmark
│       └── MTTKRPSmall.scala           # 20×20×20 tensor benchmark
│
├── data/                                # Test datasets
│   └── raw-format/
│       ├── small/
│       │   ├── sparse_matrix_small.mtx
│       │   └── dense_vector_small.txt
│       └── large/
│           ├── sparse_matrix_large.mtx  # 1000×1000, 300K non-zeros
│           └── dense_vector_large.txt   # 1000 elements
│
├── results/                             # Benchmark outputs (auto-generated)
│   ├── microbench_small.csv
│   ├── microbench_large.csv
│   ├── spmm_sparse_small.csv
│   ├── spmm_sparse_large.csv
│   ├── mttkrp_small.csv
│   ├── mttkrp_large.csv
│   └── scalability.csv
│
├── build.sbt                            # SBT build configuration
├── project/
│   └── build.properties                 # SBT version specification
└── README.md                            # This file
```

---

## 👥 Contributors

- **Harsh Mehta** - s2816523
- **Rowan Clarke** - s2190337
- **Govind Saraswat** - s2808241

