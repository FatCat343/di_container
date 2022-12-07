package example

import di.DependencyProvider

class TestClass {
    fun test() {
        val int: DependencyProvider<Car> = Car_DependencyProvider(Engine(),Wheel())
        int.get()
    }

}
fun main(args: Array<String>) {
    val int: DependencyProvider<Car> = Car_DependencyProvider(Engine(),Wheel())
    int.get()
}
