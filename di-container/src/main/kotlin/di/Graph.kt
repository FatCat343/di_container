package di

import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror

data class Graph(
    val selfElement: Element,
    val functionsElements: List<Function>,
) {
    val type: TypeMirror
        get() = selfElement.enclosingElement.asType()

    val name: String
        get() = selfElement.enclosingElement.simpleName.toString()
}
data class Function(
    val selfElement: Element,
    val outputElement: TypeMirror,
) {
}