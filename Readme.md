# Build Challenge - 

## **Assignment 1: Producer-Consumer Pattern**

| Objective | Implementation | Status |
|-----------|----------------|--------|
| Thread synchronization | Shared queue + poison pill | ✅ |
| Concurrent programming | Multi-threaded producer/consumer | ✅ |
| Blocking queues | `ArrayBlockingQueue` | ✅ |
| Wait/Notify mechanism | `ReentrantLock` + `Condition` | ✅ |
## 🚀 Quick Start

`git clone https://github.com/p1yu5h0/build-challenge.git
`
`cd build-challenge-parent/`

`cd assignment1-producer-consumer/`

#### Run ALL TESTS
`mvn clean test`

#### Run BlockingQueue demo
`mvn exec:java -Dexec.mainClass="com.piyush.assignment1.ProducerConsumerApp"
`
#### Run Wait/Notify demo
`mvn exec:java -Dexec.mainClass="com.piyush.assignment1.ProducerConsumerWaitNotify" -Dexec.args="demo"
`
#### Build JAR
`mvn clean package java -cp target/assignment1-producer-consumer-1.0.0.jar com.piyush.assignment1.ProducerConsumerApp`

🧪 Test Results

`mvn clean test`

Expected output:

`[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0`

`[INFO] BUILD SUCCESS`

## Tests cover:

1. `blockingQueue_allItemsTransferredInOrder()` - Data integrity + order preservation
3. `blockingQueue_consumerStopsOnPoisonPill()` - Proper termination
5. `blockingQueue_poisonPillRemainsInQueueForOtherConsumers()` - Concurrency
7. `waitNotifyMechanismWorks()` - Wait/Notify correctness

## 🔧 IntelliJ Setup
1.	Open `build-challenge-parent` as Maven project
2.	Run `mvn clean compile` (Maven tool window)
3.	Right-click each main class → `Run 'Main.main()'`
4.	Right-click test class → `Run 'ProducerConsumerAppTest'`


## 🛠 Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Core language |
| **Maven** | 3.9+ | Build automation |
| **JUnit** | 5.10.2 | Unit testing |
| **ArrayBlockingQueue** | JDK | Thread-safe queue |
| **ReentrantLock** | JDK | Wait/Notify synchronization |

## 📤 Submission Checklist
1. 	All 4 objectives covered
2. 	4/4 tests pass (`mvn clean test`)
3. 	Both demos run (BlockingQueue + Wait/Notify)
4. 	Production-ready code (error handling, logging, Javadoc)
5. 	Maven multi-module structure
6. 	Complete README with commands

