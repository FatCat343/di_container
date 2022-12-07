package di

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element

object GraphsGenerator {
    fun generateDependencyProviderClass(graph: Graph): TypeSpec {
        println("graph type = ${graph.selfElement.enclosingElement.simpleName}")
        /*return TypeSpec.interfaceBuilder("${graph.name}_Graph")
            .addSuperinterface(graph.selfElement.asType().asTypeName())
            .build()*/
        val a = TypeSpec.classBuilder("${graph.name}_Graph")
            .addSuperinterface(graph.selfElement.asType().asTypeName())
        for (i in graph.functionsElements.indices){
            a.addFunction(
                FunSpec.builder(graph.functionsElements[i].selfElement.simpleName.toString())
                    .addModifiers(KModifier.OVERRIDE)
                    .returns(graph.functionsElements[i].outputElement.asTypeName())
                    .addStatement("return %T(${"hhj"})", graph.functionsElements[i].outputElement)
                    .build()
            )
        }
        return a.build()

    }
    fun createArgsString(dependencies: List<Element>): String {
        return dependencies.joinToString(separator = ", ") { it.simpleName }
    }
}