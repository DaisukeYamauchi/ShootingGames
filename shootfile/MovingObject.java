import java.awt.Graphics;

abstract class MovingObject{//抽象クラス

int x;//中心座標
int y;//中心座標
int dx;//速度
int dy;//速度
int w;//横幅の半分
int h;//堪幅の半分
int hp;//hitpoint  hp<=0で死亡
public int[] cPoint; //shoot point をカウントする変数
MovingObject(){}

MovingObject(int width,int height){//コンストラクタ

x=(int)(Math.random()*width);
y=(int)(Math.random()*height);
dx=(int)(Math.random()*6-3);
dy=(int)(Math.random()*6-3);
w=2;
h=2;
hp=10;
}
void cGetImage(GameMaster ap){};//オブジェクトの画像を取り込むメソッド

abstract void move(Graphics buf, int apWidth,int apHeight);//オブジェクトの移動の更新の抽象メソッド

abstract void revive(int w,int h);//オブジェクトを生き返らせる抽象メソッド

boolean collisionCheck(MovingObject obj){

if(Math.abs(this.x-obj.x)<=(this.w+obj.w) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定
  this.hp--;//自分のHPを減らす
  obj.hp--;//相手のHPを減らす
  return true;
  }
  else
  return false;
  }
  
  boolean collisionCheck(MovingObject obj,int[] cPoint){//オーバーロード

if(Math.abs(this.x-obj.x)<=(this.w+obj.w) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定
  this.hp--;
  obj.hp--;
  cPoint[0]++;//shoot point 加算
  return true;
  }
  else
  return false;
  }
  
   }
  
  

