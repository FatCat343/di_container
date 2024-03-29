package di

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.lang.model.element.Element

object DependencyProviderGenerator {

    private val dependencyProviderClassName = ClassName("di", "DependencyProvider")

    @OptIn(DelicateKotlinPoetApi::class)
    fun generateDependencyProviderClass(dependency: Dependency): TypeSpec {
        println("dep type = ${dependency.selfElement.enclosingElement.simpleName}")
        return TypeSpec.classBuilder("${dependency.name}_DependencyProvider")
            .addSuperinterface(dependencyProviderClassName.parameterizedBy(dependency.type.asTypeName()))
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .let { cb ->
                        dependency.dependenciesElements.forEach {
                            cb.addParameter(
                                ParameterSpec(
                                    name = it.simpleName.toString(),
                                    type = it.asType().asTypeName(),
                                )
                            )
                        }
                        cb
                    }
                    .build()
            )
            .addProperties(
                dependency.dependenciesElements.map {
                    PropertySpec.builder(it.simpleName.toString(), it.asType().asTypeName())
                        .initializer(it.simpleName.toString())
                        .build()
                }
            )
            .addFunction(
                FunSpec.builder("get")
                    .addModifiers(KModifier.OVERRIDE)
                    .returns(dependency.type.asTypeName())
                    .addStatement("return %T(${createArgsString(dependency.dependenciesElements)})", dependency.type)
                    .build()
            )
            .build()
    }

    private fun createArgsString(dependencies: List<Element>): String {
        return dependencies.joinToString(separator = ", ") { it.simpleName }
    }
}