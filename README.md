[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-f4981d0f882b2a3f0472912d15f9806d57e124e0fc890972558857b51b24a6f9.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=10650448)
# CS1501 Project 4

## Goal:
To gain a better understanding of graphs and graph algorithms through practical
implementation.


## High-level description:
Your program will analyze a given graph representing a computer network
according to several specified metrics. The vertices of these graphs will
represent switches in the network, while the edges represent either fiber optic
or copper cables run between the switches.


## Specifications:
1. You will need to implement a class named `NetAnalysis` in
	`./app/src/main/java/cs1501_p4/NetAnalysis.java` which implements
	`NetAnalysis_Inter`. This class will store your graph representation and
	implement the following methods required by the project (for further
	descriptions of these methods, refer to the JavaDoc comments in
	`NetAnalysis_Inter.java`):

	1. `lowestLatencyPath(int u, int w);`

		* You must find the path between these vertices that will require the
			least amount of time for a single data packet to travel. For this
			project, we will simply compute the time required to travel along a
			path through the graph as the sum of the times required to travel each
			edge, where the time to travel each edge is computed as the length of
			the cable represented by that edge divided by the speed at which data
			can be send along a connection of that type.

			* A single data packet can be sent along a copper cable at a speed
			  of 230000000 meters per second.

			* A single data packet can be sent along a fiber optic cable at a
			  speed of 200000000 meters per second.

	1. `bandwidthAlongPath(ArrayList<Integer>);`

	1. `copperOnlyConnected();`

	1. `connectedTwoVertFail();`

	1. `lowestAvgLatST();`

1. Your `NetAnalysis` should further provide a constructor that acceps a sting
	String argument that specifies the name of a file containing a description of a
	graph. Two such files are provided (`network_data1.txt` and
	`network_data2.txt`). The format of these files is as follows:

	* The first line contains a single int stating the number of vertices in
	  the graph. These vertices will be numbered 0 to v-1.

	* Each following line will describe a single edge in the graph, with each
	  of the following data items listed separated by spaces.

		* First, two integers specify the endpoints of the edge.
		* Next, a string describes the type of cable that edge represents
		  (either "optical" or "copper").
		* Next, an integer states the bandwidth of the cable in megabits per
		  second.
		* Finally, an integer states the length of the cable in meters.
		* E.g., the line `0 5 optical 10000 25` describes an edge between
		  vertex 0 and vertex 5 that represents a 25 meter long optical cable
		  with bandwidth of 10 gigabits per second.

	* You should assume that all cables are full duplex and hence represent
	  connections in both directions (e.g., in the example above data can flow
	  from vertex 0 to vertex 5 at 10 gigabits per second and from vertex 5 to
	  vertex 0 at 10 gigabits per second simultaneously).

1. Internally, `NetAnalysis` must represent the graph as an adjacency list.
	Whether you represent the graph as a directed or undirected graph is up to
	you.


## Submission Guidelines:
* **DO NOT** add the `./app/build/` diectory to your repository.
	* Leave the `./app/build.gradle` file there, however

* Be sure to remember to push the latest copy of your code back to your GitHub
	repository before submitting. To submit, log into GradeScope from Canvas and
	have GradeScope pull your repository from GitHub.


## Additional Notes/Hints:
* You are free to use code provided by the book authors in implementing your
  solution. It is up to you to decide if it would be easier to modify the
  provided code to meet the requirements of this project or if it would be
  easier to start with a clean slate with all of your own code. You cannot use
  any JCL classes (with the following exceptions: file I/O, and using ArrayList
  as needed by the provided interfaces).

* The assumed calculation of network latency used here is a drastic
  simplification for this project. Interested students are encouraged to
  investigate a more detailed study of computer networks independently
  (recommended reading: _Computer Networks: A Systems Approach_ by Peterson
  and Davie).


## Grading Rubric
| Feature | Points
| ------- | ------
| Graph is properly read and represented | 10
| Lowest latency path computation | 15
| Bandwidth along a path correctly determined | 10
| Copper-only connectivity | 15
| Surviving 2 vertex failures | 25
| Minimum average latency spanning tree | 20
| Proper assignment submission | 5
