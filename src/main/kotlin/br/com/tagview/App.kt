package br.com.tagview

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
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

        GL11.glViewport(0, 0, 1280, 720)

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

    private var vertexShader = 0
    private var fragmentShader = 0
    private var program = 0

    private fun prepare() {
        val vertexShaderSrc =
            """
                attribute vec2 in_position;
                varying vec2 fragment_position;
                
                void main(void) {
                    gl_Position = vec4(in_position.xy, 0.0, 1.0);
                    fragment_position = in_position;
                }
            """.trimIndent()

        vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)
        GL20.glShaderSource(vertexShader, vertexShaderSrc)
        GL20.glCompileShader(vertexShader)

        val vertexShaderCompileStatus = GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS)

        if (vertexShaderCompileStatus == 0) {
            print("Failed compiling vertext shader")
            println(GL20.glGetShaderInfoLog(vertexShader))
        }

        val fragmentShaderSrc =
            """
                varying vec2 fragment_position;
                
                void main(void) {
                    float y = (fragment_position.y + 0.5) * 9.0;
                    
                    float r = 0.0;
                    float g = 0.0;
                    float b = mod(y, 3.0) / 3.0;
                    
                    if (b < 0.5 && y < 4.5) {
                        g = 1.0 - (b * 2.0);
                        r = g * (y / 4.5);
                    }
                    
                    if (b < 0.5 && y >= 4.5) {
                        r = 1.0 - (b * 2.0);
                    }
                    
                    gl_FragColor = vec4(r, g, b, 1.0);
                }
            """.trimIndent()

        fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)
        GL20.glShaderSource(fragmentShader, fragmentShaderSrc)
        GL20.glCompileShader(fragmentShader)

        val fragmentShaderCompileStatus = GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS)

        if (fragmentShaderCompileStatus == 0) {
            print("Failed compiling fragment shader")
            println(GL20.glGetShaderInfoLog(fragmentShader))
        }

        program = GL20.glCreateProgram()
        GL20.glAttachShader(program, vertexShader)
        GL20.glAttachShader(program, fragmentShader)

        GL20.glBindAttribLocation(program, 0, "in_position")

        GL20.glLinkProgram(program)
        GL20.glValidateProgram(program)

        val errorCheckValue = GL11.glGetError()

        if (errorCheckValue != GL11.GL_NO_ERROR) {
            println("ERROR - Could not validate the program: $errorCheckValue")
        }

        GL20.glUseProgram(program)

        val coordinates = floatArrayOf(
            0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f
        )

        val buffer = BufferUtils.createFloatBuffer(coordinates.size)

        coordinates.forEach { buffer.put(it) }

        buffer.flip()

        val totalVertex = 2

        GL20.glVertexAttribPointer(0, totalVertex, GL20.GL_FLOAT, false, 0, buffer)
        GL20.glEnableVertexAttribArray(0)
    }

    private fun loop() {
        val firstVertexIndex = 0
        val vertexCount = 3
        GL11.glDrawArrays(GL11.GL_TRIANGLES, firstVertexIndex, vertexCount)
    }

    private fun finish() {
        GL20.glUseProgram(0)
        GL20.glDeleteProgram(program)
        GL20.glDeleteShader(vertexShader)
        GL20.glDeleteShader(fragmentShader)
    }
}