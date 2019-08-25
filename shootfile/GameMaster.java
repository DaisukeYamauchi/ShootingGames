import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;




public class GameMaster extends Canvas implements KeyListener{

 Image mimg,ling,ong,cp1,cp2,li,ryu,hinotama,syurikenn,cle,bossHPB,bossfield;// 仮の画用紙, マップイメージ
Image buf;
Graphics buf_gc;
Dimension d;
private int imgW, imgH;//キャンバスの大きさ
private int enmyAnum =5;//敵Aの数
private int enmyBnum=5;//敵Bの数
private int luckitnum=1;//回復アイテムの数
private int bossnum=1;//ボスの数
private int ftrBltNum=10;//招き猫の小判の数
private int bossBltNum=10;//ボスの火の玉の数
private int ninjaBltNum=5;//忍者が投げてくるクナイの数
private int mode =-1;// -3: ゲームクリア画面 -2: ゲームオーバー画面 -1: タイトル画面 1~ ゲームステージ
private int i,j; 
private int bcount;//ボスの弾管理
private int set=0;// ゲームのリセット管理
public int[] cPoint={0};//配列でおく　参照渡しで敵の撃破数をカウントするため 
GameSounds test =new GameSounds(); //ゲーム内の効果音を再生するためのオブジェクト生成



Fighter ftr;//招き猫
FighterBullet ftrBlt[]= new FighterBullet[ftrBltNum];//小判
BossBullet bossBlt[]=new BossBullet[bossBltNum];//火の玉
NinjaBullet ninjaBlt[]=new NinjaBullet[ninjaBltNum];//クナイ
MovingObject enmyA[]=new EnemyA[enmyAnum];//敵A
MovingObject enmyB[]=new EnemyB[enmyBnum];//敵B
MovingObject boss[]=new Boss[bossnum];//ボス(ドラゴン)
Luckyitem luit;//回復アイテム
GameMaster(int imgW,int imgH){

this.imgW=imgW;//引数として取得した描画サイズをローカルな変数に代入
this.imgH=imgH;//引数として取得した描画サイズをローカルな変数に代入
setSize(imgW,imgH);//サイズ設定

addKeyListener(this);

 mimg = this.getToolkit().getImage("tatami01.png");//以下画像を読み込み
 bossfield= this.getToolkit().getImage("sky.jpg");
ong=this.getToolkit().getImage("koban.gif");
ling= this.getToolkit().getImage("E5908DE58FA4E5B18BE59F8E.jpg");
cp1 = this.getToolkit().getImage("122639m.png");
cp2 = this.getToolkit().getImage("122651m.png");
li =this.getToolkit().getImage("samu_tool_01_d_r4_c92.png");
ryu = this.getToolkit().getImage("pics1308.png");
hinotama = Toolkit.getDefaultToolkit().createImage("images.png");
syurikenn = Toolkit.getDefaultToolkit().createImage("122558s.png");
cle = this.getToolkit().getImage("img_post152_01.jpg");
bossHPB = this.getToolkit().getImage("2659051.png");

ftr=new Fighter(imgW,imgH);//以下オブジェクト生成
for(i=0;i<ftrBltNum;i++)
ftrBlt[i]=new FighterBullet();
for(i=0;i<enmyAnum;i++)
enmyA[i] =new EnemyA(imgW,imgH);
for(i=0;i<ninjaBltNum;i++)
ninjaBlt[i]=new NinjaBullet();
for(i=0;i<bossnum;i++)
boss[i] =new Boss(imgW,imgH);
for(i=0;i<bossBltNum;i++)
bossBlt[i]=new BossBullet();
for(i=0;i<enmyBnum;i++)
enmyB[i] =new EnemyB(imgW,imgH);
luit=new Luckyitem(imgW,imgH);
ftr.cGetImage(this);//以下画像取得
for(i=0;i<ftrBltNum;i++)//
ftrBlt[i].cGetImage(this);//
for(i=0;i<enmyAnum;i++)//
enmyA[i].cGetImage(this);//
for(i=0;i<enmyBnum;i++)
enmyB[i].cGetImage(this);
for(i=0;i<bossnum;i++)
boss[i].cGetImage(this);
for(i=0;i<bossBltNum;i++)
bossBlt[i].cGetImage(this);
for(i=0;i<ninjaBltNum;i++)
ninjaBlt[i].cGetImage(this);
luit.cGetImage(this);

}
public void addNotify(){
super.addNotify();
buf=createImage(imgW,imgH);//buffer作成
buf_gc=buf.getGraphics();
}

public void paint(Graphics g){


switch (mode){

case -3://ゲームクリア画面（スペースキーでタイトル画面）
test.bgm2.stop();//ボス戦の効果音ストップ
test.clearbgm.start();//ゲームクリアの効果音開始
buf_gc.drawImage(cle, 0, 0, imgW, imgH, this);//画像貼り付け
buf_gc.setColor(Color.yellow);//文字の色
buf_gc.setFont(new Font("Serif" , Font.BOLD + Font.ITALIC , 50));//文字のフォント、大きさ変更
buf_gc.drawString(" ==GAME CLEAR == ",imgW/2-230 ,imgH/2-20);
ftr.hp=10;//招き猫のHP更新
break;
case -2://ゲームオーバー画面(スペースキーでタイトル画面)
test.bgm1.stop();//効果音ストップ
test.bgm2.stop();//効果音ストップ
test.gameoverbgm.start();//ゲームオーバー用効果音開始
buf_gc.drawImage(ling, 0, 0, imgW, imgH, this);//画像はりつけ
buf_gc.setColor(Color.red);//文字の色
buf_gc.drawString(" ==Game over ==",imgW/2,imgH/2-20);
set=0;
buf_gc.drawString(  " Shoot Point    "+String.valueOf(cPoint[0]),imgW/2,imgH/2+20);
buf_gc.drawString(  " Hit SPACE key   ",imgW/2,imgH/2+60);
break;

case -1:




test.gameoverbgm.stop();//もしゲームオーバー画面から移動してきたなら止める
test.clearbgm.stop();//もしクリア画面から移動してきたなら止める
test.gameoverbgm.flush();//バッファ消去
test.gameoverbgm.setFramePosition(0);//再生位置の初期化
test.clearbgm.flush();//バッファ消去
test.clearbgm.setFramePosition(0);//再生位置の初期化
test.bgm1.start();
test.bgm1.loop(Clip.LOOP_CONTINUOUSLY);
buf_gc.drawImage(ling, 0, 0, imgW, imgH, this);
buf_gc.setColor(Color.black);
buf_gc.setFont(new Font("Serif" , Font.BOLD + Font.ITALIC , 30));//フォントの種類
buf_gc.drawString(" ==招き猫　倒幕伝 == ",imgW/2,imgH/2-20);//タイトル名

buf_gc.drawString("Hit SPACE bar to start game",imgW/2,imgH/2+20);
cPoint[0]=0;//pointリセット

break;
default://ゲーム中
if(set==0){//HP更新管理
ftr.hp=10;}
if(boss[0].hp==0)buf_gc.drawImage(mimg, 0, 0, imgW, imgH,this);//忍者戦（敵A,敵B）の背景
else if(boss[0].hp==1||boss[0].hp==2||boss[0].hp==3||boss[0].hp==4||boss[0].hp==5){
test.bgm1.stop();//最初のBGM停止して
test.bgm2.start();//ボス戦用BGM
test.bgm2.loop(Clip.LOOP_CONTINUOUSLY);//ループ



buf_gc.drawImage(bossfield, 0, 0, imgW, imgH, this);//画像貼り付け


}

buf_gc.setColor(Color.red);//文字色
buf_gc.drawString("MY LIFE  "+String.valueOf(ftr.hp),imgW-200,30);
buf_gc.drawString(  " Shoot Point    "+String.valueOf(cPoint[0]),imgW-450,30);
if(cPoint[0]==10){//ボス戦へ行くための条件
buf_gc.setColor(Color.red);
buf_gc.drawString(" ==BOSS出現 == ",imgW/2-100 ,imgH/2-20);//ボス出現時表示
}
int boox,booy;//ボスのHPバーの間隔取るための数
boox=2;//ボスの残りライフ表示間隔とる変数
booy=10;//ボスの残りライフ表示間隔とる変数
if(boss[0].hp==5){//ボスのHPバー表示 HPが減るたびに貼り付ける画像数が減る
buf_gc.drawImage(bossHPB, boox, booy, boox+30, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+40, booy, boox+70, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+80, booy, boox+110, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+120, booy, boox+150, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+160, booy, boox+190, booy+30, 0, 0, 404, 379, this);
}
else if(boss[0].hp==4){
buf_gc.drawImage(bossHPB, boox, booy, boox+30, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+40, booy, boox+70, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+80, booy, boox+110, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+120, booy, boox+150, booy+30, 0, 0, 404, 379, this);
}

else if(boss[0].hp==3){
buf_gc.drawImage(bossHPB, boox, booy, boox+30, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+40, booy, boox+70, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+80, booy, boox+110, booy+30, 0, 0, 404, 379, this);
}
else if(boss[0].hp==2){
buf_gc.drawImage(bossHPB, boox, booy, boox+30, booy+30, 0, 0, 404, 379, this);
buf_gc.drawImage(bossHPB, boox+40, booy, boox+70, booy+30, 0, 0, 404, 379, this);
}
else if(boss[0].hp==1){
buf_gc.drawImage(bossHPB, boox, booy, boox+30, booy+30, 0, 0, 404, 379, this);

}



ftr.cDraw(buf_gc);//招き猫貼り付け
ftr.move(buf_gc,imgW,imgH);//招き猫移動



set++;//LIFEリセット防止

makeEnmy:if(Math.random()<0.1){//ランダムに生成
for(i=0;i<enmyAnum;i++){
if(enmyA[i].hp==0&&boss[0].hp==0){//ボス戦では出現させないため
enmyA[i].revive(imgW/2,imgH/2);

break makeEnmy;}}}

makeEnmy: if(cPoint[0]==10){//ボスの出現条件

for(i=0;i<bossnum;i++){
if(boss[i].hp==0){
boss[i].revive(imgW,imgH);
break makeEnmy;}}}

makeItem:if(Math.random()<0.001){//ラッキーアイテムは低確率
if(luit.hp==0&&boss[0].hp==0)
luit.revive(imgW,imgH);
break makeItem;}






makeEnmy:if(Math.random()<0.1){//ランダム生成
for(i=0;i<enmyBnum;i++){
if(enmyB[i].hp==0&&boss[0].hp==0){//ボス戦では出現させない
enmyB[i].revive(imgW,imgH);
break makeEnmy;
}}
}


if(ftr.sflag ==true && ftr.delaytime==0){//もしスペースキー押されて＆待ち時間ゼロなら

for(i=0;i<ftrBltNum;i++){
if(ftrBlt[i].hp==0){
ftrBlt[i].revive(ftr.x+42,ftr.y);//小判発射
test.clip.start();//BGM再生
test.clip.flush();//バッファ消去
test.clip.setFramePosition(0);//再生位置の初期化

ftr.delaytime=5;

break;
}
}
}
else if(ftr.delaytime >0)
  ftr.delaytime--;
  
  for(i=0;i<enmyAnum;i++)//全ての敵Aに対し
  if(enmyA[i].hp>0){//敵が生きていたら
  ftr.collisionCheck(enmyA[i]);//衝突チェック
  for(j=0;j<ftrBltNum; j++)//全ての小判に対し
  if(ftrBlt[j].hp>0){//小判が生きていたら
  ftrBlt[j].collisionCheck(enmyA[i],cPoint);//小判と衝突チェック(cPointのポインターを引数とすることで参照渡しを行う。)
  
  
    
  }
  
  
  
  }
  for(i=0;i<bossnum;i++)//ボスに対し
  if(boss[i].hp>0){//ボスが生きていたら、
  ftr.collisionCheck(boss[i]);//招き猫と衝突判定
  for(j=0;j<ftrBltNum; j++)
  if(ftrBlt[j].hp>0)//小判が生きていたら
  ftrBlt[j].collisionCheck(boss[i],cPoint);//小判と衝突チェック
  
  }



    for(i=0;i<enmyBnum;i++)//全ての敵Bに対して
  if(enmyB[i].hp>0){//敵が生きていたら
  ftr.collisionCheck(enmyB[i]);//招き猫との衝突チェック
  for(j=0;j<ftrBltNum; j++)//全ての小判に対して
  if(ftrBlt[j].hp>0){//小判が生きていたら
  ftrBlt[j].collisionCheck(enmyB[i],cPoint);//小判との衝突チェック
  }
  }
  
  //敵の弾を発射
bcount=(int)(Math.random()*2);//ボスの火の玉がランダムに降ってくるようにする
  if(bcount==0 && boss[0].hp!=0){//bcountが0(1/2の確率)で
    for(i=0;i<bossBltNum;i++)
      if(bossBlt[i].hp==0){
        bossBlt[i].revive(boss[0].x,boss[0].y);//火の玉発射
      test.bossbress.start();//bgmスタート
      test.bossbress.flush();//バッファ消去
      test.bossbress.setFramePosition(0);//再生位置の初期化
      break;}

  }
 for(int k=0; k<enmyAnum;k++)//全ての敵Aに対し
 if(bcount==0 && enmyA[k].hp!=0){//bcountが0なら
    for(i=0;i<ninjaBltNum;i++)//全てのクナイに対し
      if(ninjaBlt[i].hp==0){//非アクティブのクナイがあれば
        ninjaBlt[i].revive(enmyA[k].x,enmyA[k].y);//投げる
      break;}
  }

  

 if (ftr.hp>0){//招き猫が生きている時


 for(j=0;j<bossBltNum; j++)//全ての火の玉に対し
  if(bossBlt[j].hp>0)//火の玉が生きていたら
  bossBlt[j].collisionCheck(ftr);//招き猫との衝突チェック
  
  
  for(j=0;j<ninjaBltNum; j++)//全てクナイに対し
  if(ninjaBlt[j].hp>0)//クナイが生きていたら
  ninjaBlt[j].collisionCheck(ftr);//招き猫との衝突チェック
  
  
  if(luit.hp>0)//宝箱とのあたり判定
  luit.collisionCheck(ftr);
   
  }
  
  
  
  if(ftr.hp<1)//招き猫のHPが無くなったら
  mode=-2;//ゲームオーバー画面に移行

 
  //オブジェクトの描画と移動管理
  for(i=0;i<enmyAnum;i++){
    enmyA[i].move(buf_gc,imgW,imgH);}
  for(i=0;i<enmyBnum;i++){
    enmyB[i].move(buf_gc,imgW,imgH);}
  for(i=0;i<bossnum;i++){
    boss[i].move(buf_gc,imgW,imgH);}
  for(i=0;i<ftrBltNum;i++)
    ftrBlt[i].move(buf_gc,imgW,imgH);

  for(i=0;i<bossBltNum;i++)
    bossBlt[i].move(buf_gc,imgW,imgH);
  for(i=0;i<ninjaBltNum;i++)
    ninjaBlt[i].move(buf_gc,imgW,imgH);
    luit.move(buf_gc,imgW,imgH);


if( boss[0].hp==0 &&cPoint[0]>=11)mode=-3;//ボス画面に移行する条件


//状態チェック
for(i=0;i<enmyAnum;i++){
  System.out.print(enmyA[i].hp+" ");}
  System.out.println("");
for(i=0;i<enmyBnum;i++){
  System.out.print(enmyB[i].hp+" ");}
  System.out.println("");
for(i=0;i<bossnum;i++){
  System.out.print(boss[i].hp+"  ");}
  System.out.println("");
}g.drawImage(buf,0,0,imgW,imgH,this);//表の画用紙に裏の画用紙の内容貼り付ける

}



public void update(Graphics gc){//repaint()で呼ばれる
paint(gc);
}
public void keyTyped(KeyEvent ke){//書かないとエラー

}

public void keyPressed(KeyEvent ke){
int cd =ke.getKeyCode();
switch (cd){
 case KeyEvent.VK_LEFT://<-キー押すと
ftr.lflag=true;//フラグ立てる
break;
 case KeyEvent.VK_RIGHT://->キー押すと
ftr.rflag=true;//フラグ立てる
break;
 case KeyEvent.VK_UP://↑押すと
ftr.uflag=true;//フラグ立てる
break;
 case KeyEvent.VK_DOWN://↓押すと
ftr.dflag=true;//フラグ立てる
break;
 case KeyEvent.VK_SPACE://スペースキー押すと
ftr.sflag=true;//フラグ立てる
if(this.mode !=1&&this.mode!=-3){//ゲームクリア画面以外からタイトル画面に戻った時
this.mode++;
}

if(this.mode ==-3){//ゲームクリア画面からタイトル画面に戻る時
this.mode+=2;}

break;
}}

public void keyReleased(KeyEvent ke){
int cd =ke.getKeyCode();
switch (cd){
 case KeyEvent.VK_LEFT://<-キーが離されたら
ftr.lflag=false;//フラグ下ろす
break;
 case KeyEvent.VK_RIGHT://->キー離されたら
ftr.rflag=false;//フラグ下ろす
break;
 case KeyEvent.VK_UP://↑キーが離されたら
ftr.uflag=false;//フラグ下ろす
break;
 case KeyEvent.VK_DOWN://↓キーが離されたら
ftr.dflag=false;//フラグ下ろす
break;
 case KeyEvent.VK_SPACE://スペースキーが離されたら
ftr.sflag=false;//フラグ下ろす

break;
}}}










