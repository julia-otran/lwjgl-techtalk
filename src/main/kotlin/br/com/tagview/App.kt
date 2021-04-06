package br.com.tagview

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import java.lang.RuntimeException

class App() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            App()
        }
    }

    init {
        if (!GLFW.glfwInit()) {
            throw RuntimeException("Cannot init GLFW")
        }

        val window = GLFW.glfwCreateWindow(1280, 720, "TechTalk OpenGL", 0L, 0L)

        if (window == 0L) {
            throw RuntimeException("Cannot create window")
        }

        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1)

        GL.createCapabilities()

        prepare()

        while (!GLFW.glfwWindowShouldClose(window)) {
            loop()
            GLFW.glfwSwapBuffers(window)
            GLFW.glfwPollEvents()
        }

        finish()

        GLFW.glfwDestroyWindow(window)
        GLFW.glfwTerminate()
    }

    private fun prepare() {

    }

    private fun loop() {

    }

    private fun finish() {

    }
}