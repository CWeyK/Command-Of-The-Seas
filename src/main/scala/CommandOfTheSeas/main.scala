package CommandOfTheSeas

import CommandOfTheSeas.view._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.media.AudioClip


object Main extends JFXApp {

  // Method to load FXML file and return the root and controller
  private def loadFXML[A](fileName: String): (jfxs.Parent, A) = {
    val resource = getClass.getResource(fileName)
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.Parent]
    val controller = loader.getController[A]
    (roots, controller)
  }
  // Define controllers
  var startMenuController: Option[StartMenuController#Controller] = None
  var gameController: Option[GameController#Controller] = None
  var gameOverController: Option[GameOverController#Controller] = None
  var tutorial1Controller: Option[Tutorial1Controller#Controller] = None
  var tutorial2Controller: Option[Tutorial2Controller#Controller] = None
  var tutorial3Controller: Option[Tutorial3Controller#Controller] = None

  private def loadAndShowScene[A](fileName: String): (jfxs.Parent, A) = {
    val (root2, controller) = loadFXML[A](fileName)
    stage = new PrimaryStage {
      title = "Command Of The Seas"
      scene = new Scene {
        stylesheets += getClass.getResource("/CommandOfTheSeas/view/Theme.css").toString
        root = root2
      }
    }
    (root2, controller)
  }


  //Show starting main menu
  def showStartMenu(): Unit = {
    val (_, controller) = loadAndShowScene[StartMenuController#Controller]("view/StartMenu.fxml")
    startMenuController = Some(controller)
  }



  //Show game when press play
  def showGame() : Unit = {
    val (_, controller) = loadAndShowScene[GameController#Controller]("view/Game.fxml")
    gameController = Some(controller)
  }

  //Show game over screen
  def showGameOver(winner: String) : Unit = {
    val (_, controller) = loadAndShowScene[GameOverController#Controller]("view/GameOver.fxml")
    gameOverController = Some(controller)
    gameOverController.foreach(_.setWinner(winner))
  }

  //Show tutorial pg 1
  def showTutorial1() : Unit = {
    val (_, controller) = loadAndShowScene[Tutorial1Controller#Controller]("view/Tutorial1.fxml")
    tutorial1Controller = Some(controller)
  }

  //Show tutorial pg 2
  def showTutorial2() : Unit = {
    val (_, controller) = loadAndShowScene[Tutorial2Controller#Controller]("view/Tutorial2.fxml")
    tutorial2Controller = Some(controller)
  }

  //Show tutorial pg 3
  def showTutorial3() : Unit = {
    val (_, controller) = loadAndShowScene[Tutorial3Controller#Controller]("view/Tutorial3.fxml")
    tutorial3Controller = Some(controller)
  }

  //Play bgm on loop
  private val bgm: AudioClip = new AudioClip(getClass.getResource("/CommandOfTheSeas/sound/bgm.mp3").toString)
  bgm.volume = 0.2
  bgm.cycleCount = AudioClip.Indefinite
  bgm.play()

  showStartMenu()
}