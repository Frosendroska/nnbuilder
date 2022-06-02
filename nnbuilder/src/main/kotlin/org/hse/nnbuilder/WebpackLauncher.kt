package org.hse.nnbuilder

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.io.File
import java.util.Locale

/**
 * Webpack launcher for development environment.
 */
@Configuration
@Profile("dev")
open class WebpackLauncher {
    @Bean
    open fun frontRunner(): WebpackRunner {
        return WebpackRunner()
    }

    class WebpackRunner : InitializingBean {
        companion object {
            const val WEBPACK_SERVER_PROPERTY = "webpack-server-loaded"

            fun isWindows(): Boolean {
                return System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")
            }
        }

        override fun afterPropertiesSet() {
            if (System.getProperty(WEBPACK_SERVER_PROPERTY) !== "true") {

                startWebpackDevServer()

            }
        }

        private fun startWebpackDevServer() {
            val cmd = (if (isWindows()) listOf("cmd", "/c") else listOf()) + listOf("npm", "run", "start-dev")

            ProcessBuilder(cmd)
                .directory(File("frontend"))
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()

            System.setProperty(WEBPACK_SERVER_PROPERTY, "true")
        }
    }
}
