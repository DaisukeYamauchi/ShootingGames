import java.awt.*;
import java.awt.event.*;
public class ShootingGame extends Frame implements Runnable{

Thread th;//オブジェクト宣言
GameMaster gm;//ゲーム進行クラス


public static void main(String[] args){

  new ShootingGame();//自分自身のオブジェクト生成
  
  
  }
  
  
  ShootingGame(){
  
  super("招き猫倒幕伝");//タイトル名
  
  int cW=720,cH=560;//キャンパスのサイズ
  this.setSize(cW+30,cH+40);//フレームサイズを指定
  
  this.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));//キャンパスをフレームに配置

  gm =new GameMaster(cW,cH);//オブジェクトを生成

  
  this.add(gm);//キャンバスをフレームに配置
  this.setVisible(true);//可視化
  
  th=new Thread(this);//Threadクラスのオブジェクト生成
  th.start();//スレッド開始
  
  requestFocusInWindow();//フォーカス取得
  }
  
  
  public void run(){
  
  try{
    while(true){//無限ループ
     
    Thread.sleep(20);//休止
    gm.repaint();//再描画
    
    }}
    
    catch(Exception e){System.out.println("Eonxception:"+e);}//例外処理
    
    }}
    