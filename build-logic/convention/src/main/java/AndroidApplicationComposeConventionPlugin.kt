import com.android.build.api.dsl.ApplicationExtension
import com.llimapons.convention.configureAndroidCompose
import com.llimapons.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("rickverse.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)

            dependencies {
                "implementation"(libs.findLibrary("hilt-navigation-compose").get())
            }
        }
    }
}