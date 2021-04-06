package br.com.tagview

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
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
        GL11.glEnable(GL11.GL_COLOR_MATERIAL)
        GL11.glColor4f(1f, 0f, 1f, 1f)
    }

    private fun loop() {
        GL11.glBegin(GL11.GL_TRIANGLES)

        // By default, openGL coordinates starts at -1 and end in +1
        GL11.glVertex2d(0.0, 0.5)
        GL11.glVertex2d(-0.5, -0.5)
        GL11.glVertex2d(.5, -0.5)
        GL11.glEnd()
    }

    private fun finish() {

    }
}