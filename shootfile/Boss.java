import java.awt.*;
class Boss extends MovingObject{//ボスのクラス
Image ryu;//画像を入れる場所
int bossd=0;//ボスの動き（dx）を制御する値


Boss(int apWidth, int apHeight){//コンストラクタ
super(apWidth,apHeight);
 

w=80;//半径
h=80;
hp=0;

}
void cGetImage (GameMaster ap){//オブジェクトの画像を取り込むメソッド

ryu = Toolkit.getDefaultToolkit().createImage("pics1308.png");//画像取り込み
  }
void move(Graphics buf, int apWidth, int apHeight){//オブジェクトの位置を更新するメソッド
int amari=0;//ボスの動きを決める値bassdの余りを入れる

if(hp>0){

buf.drawImage(ryu, x, y, x+120, y+180, 0, 0, 275, 300, null);

amari=bossd%40;//bossdを40で割った余りを速さを決める指標にする
if(amari>=0&&amari<20)dx=-20;
else if(amari>=20&&amari<=39)dx=20;

bossd++;

if(bossd==2147483640)bossd=0;//int型の許容範囲外になる前に初期化


x=x+dx;//更新
y=y+dy;

if(y>=400)y=y-dy-200;//トリッキーな動き表現

if(y>apHeight+h)//画面外なら消滅
hp=0;
}}

void revive(int apWidth, int apHeight){
int bossd;//ボスの動きを決める
x=360;
y=50;
dy=0;


bossd=(int)(Math.random()*2);//ボスの動き
if(bossd==0)dx=1;
else if(bossd==1)dx=-1;



hp=5;
}}

