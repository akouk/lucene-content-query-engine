# Lucene Content Query Engine
## Description

This project is a simple content query engine built using Apache Lucene. It allows you to index files from a specified directory and then perform search queries on the indexed content. You can choose how many results you want to see, and optionally save the results to a directory.
## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed a compatible version of Java (Java 11 or later is recommended).
- You have installed Apache Maven to manage project dependencies.
- You have a basic understanding of Java and Lucene.

## Getting Started
### Cloning the Project

To clone the project, you can run the following command in your terminal:
```
git clone https://github.com/akouk/lucene-content-query-engine.git
```
### Building the Project

After cloning the project, navigate to the project directory and use Maven to compile and build the project:
```
cd <project-directory>
mvn compile
```

### Running the Project

To run the project, use the Maven exec plugin:
```
mvn exec:java -Dexec.mainClass="lucene_main.Main"
```

You will be prompted to enter the path to the index directory and the data directory, as well as the search query.

You can also specify the maximum number of search results you want to see. If there are any results, you will have the option to save them to a directory.