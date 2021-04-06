package br.com.tagview

import org.lwjgl.BufferUtils
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
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        
        GL11.glColor4f(1f, 0f, 1f, 1f)

        val coordinatesPerVertex = 2

        val coordinates = floatArrayOf(
            0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f
        )

        val buffer = BufferUtils.createFloatBuffer(coordinates.size)

        coordinates.forEach { buffer.put(it) }

        buffer.flip()

        GL11.glVertexPointer(coordinatesPerVertex, GL11.GL_FLOAT, 0, buffer)
    }

    private fun loop() {
        val firstVertexIndex = 0
        val vertexCount = 3
        GL11.glDrawArrays(GL11.GL_TRIANGLES, firstVertexIndex, vertexCount)
    }

    private fun finish() {

    }
}