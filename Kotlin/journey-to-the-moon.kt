fun journeyToMoon(n: Int, astronaut: Array<Array<Int>>): Long {
    val graph = buildGraph(astronaut)
    val visited = mutableSetOf<Int>()
    val componentCountList = mutableListOf<Int>()

    for (source in 0 until n) {
        val previousVisitedCount = visited.size
        explore(graph, source, visited)
        val currentVisitedSize = visited.size

        if (currentVisitedSize == previousVisitedCount) continue
        componentCountList.add(currentVisitedSize - previousVisitedCount)
    }

    var sum = 0L
    for (index in componentCountList.indices) {

        if (componentCountList[index] == 1) {
            sum += n - 1
            continue
        }

        val copy = componentCountList.toMutableList()
        copy.removeAt(index)

        val remainingSum = copy.sum()
        sum += componentCountList[index] * remainingSum

    }

    return sum / 2L
}


fun buildGraph(edges: Array<Array<Int>>): Map<Int, Set<Int>> {

    val graph = mutableMapOf<Int, MutableSet<Int>>()

    for (edge in edges) {
        graph[edge[0]]?.add(edge[1]) ?: graph.put(edge[0], mutableSetOf(edge[1]))
        graph[edge[1]]?.add(edge[0]) ?: graph.put(edge[1], mutableSetOf(edge[0]))
    }

    return graph
}


fun explore(graph: Map<Int, Set<Int>>, source: Int, visited: MutableSet<Int>) {

    if (source in visited) return

    visited.add(source)

    val neighbors = graph[source] ?: return
    for (neighbor in neighbors) explore(graph, neighbor, visited)

}
