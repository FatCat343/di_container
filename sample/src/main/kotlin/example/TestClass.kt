package example

import di.DependencyProvider

class TestClass {

    fun test() {
        val int: DependencyProvider<Car> = Car_DependencyProvider(Engine())
        int.get()
    }

}