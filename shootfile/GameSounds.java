import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
public class GameSounds {//ゲーム効果音全般を扱う

Clip clip = createClip(new File("se_maoudamashii_retro08.wav"));//自機弾の効果音
Clip bgm1 = createClip(new File("preparation.wav"));//道中BGM
Clip bgm2 = createClip(new File("game_maoudamashii_2_boss07 (online-audio-converter.com).wav"));//ボス戦BGM
Clip bossbress=createClip(new File("fire-storm.wav"));//ボスの攻撃効果音
Clip clearbgm = createClip(new File("Clear3 (online-audio-converter.com).wav"));//ゲームクリア音
Clip gameoverbgm = createClip(new File("tomoshibi (online-audio-converter.com).wav"));//ゲームオーバー音
Clip luckybgm =createClip(new File("fire-storm.wav"));	
		


 //以下　https://nompor.com/2017/12/14/post-128/を参考にした。

	public static Clip createClip(File path) {//wavファイルの読み込みから再生準備完了まで行う
		//指定されたURLのバイナリデータ読み込み
		try (AudioInputStream inst = AudioSystem.getAudioInputStream(path)){
			
			//ファイルの形式取得
			AudioFormat adf = inst.getFormat();
			
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,adf);
			
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);
			
			//再生準備完了
			c.open(inst);
			
			return c;
		} catch (MalformedURLException e) {//以下例外処理
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}