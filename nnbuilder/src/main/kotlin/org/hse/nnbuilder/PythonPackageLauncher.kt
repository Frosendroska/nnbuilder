package org.hse.nnbuilder

import org.hse.nnbuilder.utils.HashUtil
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path


@Configuration
open class PythonPackageLauncher {

    @Bean
    open fun pythonPackageInstaller(): PythonPackageInstaller {
        return PythonPackageInstaller()
    }

    class PythonPackageInstaller : InitializingBean {

        @Value("\${config.python.virtualenv-path}")
        internal lateinit var virtualenvPath: String

        @Value("\${config.python.package-path}")
        internal lateinit var packagePath: String

        @Value("\${config.python.package.executable}")
        internal lateinit var packageExecutableName: String

        @Autowired
        internal lateinit var resourceLoader: ResourceLoader

        override fun afterPropertiesSet() {
            if (!File(virtualenvPath).isDirectory) {
                createVirtualenv()
            }
            installPackage()
        }

        private fun createVirtualenv() {
            if (!runAndWait("python3", "-m", "venv", virtualenvPath)) {
                throw RuntimeException("Virtualenv creation failed")
            }
        }

        private fun installPackage() {
            val packageFile = resourceLoader.getResource("classpath:$packagePath").file
            val currentPackageHash = HashUtil.getFileHash(packageFile.toPath())

            val fileWithCurrentPackageHash = File("$virtualenvPath/package-hash.txt")

            if (Files.readAllLines(Path(fileWithCurrentPackageHash.absolutePath))[0] == currentPackageHash) {
                return
            }

            if (!runAndWait("$virtualenvPath/bin/pip", "install", "--force", packageFile.absolutePath)) {
                throw RuntimeException("Virtualenv creation failed")
            }

            fileWithCurrentPackageHash.createNewFile()
            fileWithCurrentPackageHash.writeText(currentPackageHash)
        }

        fun getPackageExecutablePath(): String {
            return "$virtualenvPath/bin/$packageExecutableName"
        }

        companion object {
            private fun runAndWait(vararg cmd: String): Boolean {
                return ProcessBuilder(*cmd)
                        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                        .redirectError(ProcessBuilder.Redirect.INHERIT)
                        .start()
                        .waitFor() == 0
            }
        }
    }
}
