package tw.com.bluemobile.hbc.utilities

import tw.com.bluemobile.hbc.models.City
import tw.com.bluemobile.hbc.models.Area

object Zones {

    val zones: ArrayList<HashMap<String, Any>> = arrayListOf(
        hashMapOf("id" to 1, "parent" to 0, "name" to "台北市", "zip" to 100),
        hashMapOf("id" to 2, "parent" to 1, "name" to "中正區", "zip" to 100),
        hashMapOf("id" to 3, "parent" to 1, "name" to "大同區", "zip" to 103),
        hashMapOf("id" to 4, "parent" to 1, "name" to "中山區", "zip" to 104),
        hashMapOf("id" to 5, "parent" to 1, "name" to "松山區", "zip" to 105),
        hashMapOf("id" to 6, "parent" to 1, "name" to "大安區", "zip" to 106),
        hashMapOf("id" to 7, "parent" to 1, "name" to "萬華區", "zip" to 108),
        hashMapOf("id" to 8, "parent" to 1, "name" to "信義區", "zip" to 110),
        hashMapOf("id" to 9, "parent" to 1, "name" to "士林區", "zip" to 111),
        hashMapOf("id" to 10, "parent" to 1, "name" to "北投區", "zip" to 112),
        hashMapOf("id" to 11, "parent" to 1, "name" to "內湖區", "zip" to 114),
        hashMapOf("id" to 12, "parent" to 1, "name" to "南港區", "zip" to 115),
        hashMapOf("id" to 13, "parent" to 1, "name" to "文山區", "zip" to 116),
        hashMapOf("id" to 14, "parent" to 0, "name" to "新北市", "zip" to 207),
        hashMapOf("id" to 15, "parent" to 14, "name" to "萬里區", "zip" to 207),
        hashMapOf("id" to 16, "parent" to 14, "name" to "金山區", "zip" to 208),
        hashMapOf("id" to 17, "parent" to 14, "name" to "板橋區", "zip" to 220),
        hashMapOf("id" to 18, "parent" to 14, "name" to "汐止區", "zip" to 221),
        hashMapOf("id" to 19, "parent" to 14, "name" to "深坑區", "zip" to 222),
        hashMapOf("id" to 20, "parent" to 14, "name" to "石碇區", "zip" to 223),
        hashMapOf("id" to 21, "parent" to 14, "name" to "瑞芳區", "zip" to 224),
        hashMapOf("id" to 22, "parent" to 14, "name" to "平溪區", "zip" to 226),
        hashMapOf("id" to 23, "parent" to 14, "name" to "雙溪區", "zip" to 227),
        hashMapOf("id" to 24, "parent" to 14, "name" to "貢寮區", "zip" to 228),
        hashMapOf("id" to 25, "parent" to 14, "name" to "新店區", "zip" to 231),
        hashMapOf("id" to 26, "parent" to 14, "name" to "坪林區", "zip" to 232),
        hashMapOf("id" to 27, "parent" to 14, "name" to "烏來區", "zip" to 233),
        hashMapOf("id" to 28, "parent" to 14, "name" to "永和區", "zip" to 234),
        hashMapOf("id" to 29, "parent" to 14, "name" to "中和區", "zip" to 235),
        hashMapOf("id" to 30, "parent" to 14, "name" to "土城區", "zip" to 236),
        hashMapOf("id" to 31, "parent" to 14, "name" to "三峽區", "zip" to 237),
        hashMapOf("id" to 32, "parent" to 14, "name" to "樹林區", "zip" to 238),
        hashMapOf("id" to 33, "parent" to 14, "name" to "鶯歌區", "zip" to 239),
        hashMapOf("id" to 34, "parent" to 14, "name" to "三重區", "zip" to 241),
        hashMapOf("id" to 35, "parent" to 14, "name" to "新莊區", "zip" to 242),
        hashMapOf("id" to 36, "parent" to 14, "name" to "泰山區", "zip" to 243),
        hashMapOf("id" to 37, "parent" to 14, "name" to "林口區", "zip" to 244),
        hashMapOf("id" to 38, "parent" to 14, "name" to "蘆洲區", "zip" to 247),
        hashMapOf("id" to 39, "parent" to 14, "name" to "五股區", "zip" to 248),
        hashMapOf("id" to 40, "parent" to 14, "name" to "八里區", "zip" to 249),
        hashMapOf("id" to 41, "parent" to 14, "name" to "淡水區", "zip" to 251),
        hashMapOf("id" to 42, "parent" to 14, "name" to "三芝區", "zip" to 252),
        hashMapOf("id" to 43, "parent" to 14, "name" to "石門區", "zip" to 253),
        hashMapOf("id" to 44, "parent" to 0, "name" to "基隆市", "zip" to 200),
        hashMapOf("id" to 45, "parent" to 44, "name" to "仁愛區", "zip" to 200),
        hashMapOf("id" to 46, "parent" to 44, "name" to "信義區", "zip" to 201),
        hashMapOf("id" to 47, "parent" to 44, "name" to "中正區", "zip" to 202),
        hashMapOf("id" to 48, "parent" to 44, "name" to "中山區", "zip" to 203),
        hashMapOf("id" to 49, "parent" to 44, "name" to "安樂區", "zip" to 204),
        hashMapOf("id" to 50, "parent" to 44, "name" to "暖暖區", "zip" to 205),
        hashMapOf("id" to 51, "parent" to 44, "name" to "七堵區", "zip" to 206),
        hashMapOf("id" to 52, "parent" to 0, "name" to "桃園市", "zip" to 320),
        hashMapOf("id" to 53, "parent" to 52, "name" to "中壢區", "zip" to 320),
        hashMapOf("id" to 54, "parent" to 52, "name" to "平鎮區", "zip" to 324),
        hashMapOf("id" to 55, "parent" to 52, "name" to "龍潭區", "zip" to 325),
        hashMapOf("id" to 56, "parent" to 52, "name" to "楊梅區", "zip" to 326),
        hashMapOf("id" to 57, "parent" to 52, "name" to "新屋區", "zip" to 327),
        hashMapOf("id" to 58, "parent" to 52, "name" to "觀音區", "zip" to 328),
        hashMapOf("id" to 59, "parent" to 52, "name" to "桃園區", "zip" to 330),
        hashMapOf("id" to 60, "parent" to 52, "name" to "龜山區", "zip" to 333),
        hashMapOf("id" to 61, "parent" to 52, "name" to "八德區", "zip" to 334),
        hashMapOf("id" to 62, "parent" to 52, "name" to "大溪區", "zip" to 335),
        hashMapOf("id" to 63, "parent" to 52, "name" to "復興區", "zip" to 336),
        hashMapOf("id" to 64, "parent" to 52, "name" to "大園區", "zip" to 337),
        hashMapOf("id" to 65, "parent" to 52, "name" to "蘆竹區", "zip" to 338),
        hashMapOf("id" to 66, "parent" to 0, "name" to "新竹市", "zip" to 300),
        hashMapOf("id" to 67, "parent" to 66, "name" to "東區", "zip" to 300),
        hashMapOf("id" to 68, "parent" to 66, "name" to "北區", "zip" to 300),
        hashMapOf("id" to 69, "parent" to 66, "name" to "香山區", "zip" to 300),
        hashMapOf("id" to 70, "parent" to 0, "name" to "新竹縣", "zip" to 302),
        hashMapOf("id" to 71, "parent" to 70, "name" to "竹北市", "zip" to 302),
        hashMapOf("id" to 72, "parent" to 70, "name" to "湖口鄉", "zip" to 303),
        hashMapOf("id" to 73, "parent" to 70, "name" to "新豐鄉", "zip" to 304),
        hashMapOf("id" to 74, "parent" to 70, "name" to "新埔鎮", "zip" to 305),
        hashMapOf("id" to 75, "parent" to 70, "name" to "關西鎮", "zip" to 306),
        hashMapOf("id" to 76, "parent" to 70, "name" to "芎林鄉", "zip" to 307),
        hashMapOf("id" to 77, "parent" to 70, "name" to "寶山鄉", "zip" to 308),
        hashMapOf("id" to 78, "parent" to 70, "name" to "竹東鎮", "zip" to 310),
        hashMapOf("id" to 79, "parent" to 70, "name" to "五峰鄉", "zip" to 311),
        hashMapOf("id" to 80, "parent" to 70, "name" to "橫山鄉", "zip" to 312),
        hashMapOf("id" to 81, "parent" to 70, "name" to "尖石鄉", "zip" to 313),
        hashMapOf("id" to 82, "parent" to 70, "name" to "北埔鄉", "zip" to 314),
        hashMapOf("id" to 83, "parent" to 70, "name" to "峨眉鄉", "zip" to 315),
        hashMapOf("id" to 84, "parent" to 0, "name" to "苗栗縣", "zip" to 350),
        hashMapOf("id" to 85, "parent" to 84, "name" to "竹南鎮", "zip" to 350),
        hashMapOf("id" to 86, "parent" to 84, "name" to "頭份鎮", "zip" to 351),
        hashMapOf("id" to 87, "parent" to 84, "name" to "三灣鄉", "zip" to 352),
        hashMapOf("id" to 88, "parent" to 84, "name" to "南庄鄉", "zip" to 353),
        hashMapOf("id" to 89, "parent" to 84, "name" to "獅潭鄉", "zip" to 354),
        hashMapOf("id" to 90, "parent" to 84, "name" to "後龍鎮", "zip" to 356),
        hashMapOf("id" to 91, "parent" to 84, "name" to "通霄鎮", "zip" to 357),
        hashMapOf("id" to 92, "parent" to 84, "name" to "苑裡鎮", "zip" to 358),
        hashMapOf("id" to 93, "parent" to 84, "name" to "苗栗市", "zip" to 360),
        hashMapOf("id" to 94, "parent" to 84, "name" to "造橋鄉", "zip" to 361),
        hashMapOf("id" to 95, "parent" to 84, "name" to "頭屋鄉", "zip" to 362),
        hashMapOf("id" to 96, "parent" to 84, "name" to "公館鄉", "zip" to 363),
        hashMapOf("id" to 97, "parent" to 84, "name" to "大湖鄉", "zip" to 364),
        hashMapOf("id" to 98, "parent" to 84, "name" to "泰安鄉", "zip" to 365),
        hashMapOf("id" to 99, "parent" to 84, "name" to "銅鑼鄉", "zip" to 366),
        hashMapOf("id" to 100, "parent" to 84, "name" to "三義鄉", "zip" to 367),
        hashMapOf("id" to 101, "parent" to 84, "name" to "西湖鄉", "zip" to 368),
        hashMapOf("id" to 102, "parent" to 84, "name" to "卓蘭鎮", "zip" to 369),
        hashMapOf("id" to 103, "parent" to 0, "name" to "台中市", "zip" to 400),
        hashMapOf("id" to 104, "parent" to 103, "name" to "中區", "zip" to 400),
        hashMapOf("id" to 105, "parent" to 103, "name" to "東區", "zip" to 401),
        hashMapOf("id" to 106, "parent" to 103, "name" to "南區", "zip" to 402),
        hashMapOf("id" to 107, "parent" to 103, "name" to "西區", "zip" to 403),
        hashMapOf("id" to 108, "parent" to 103, "name" to "北區", "zip" to 404),
        hashMapOf("id" to 109, "parent" to 103, "name" to "北屯區", "zip" to 406),
        hashMapOf("id" to 110, "parent" to 103, "name" to "西屯區", "zip" to 407),
        hashMapOf("id" to 111, "parent" to 103, "name" to "南屯區", "zip" to 408),
        hashMapOf("id" to 113, "parent" to 103, "name" to "太平區", "zip" to 411),
        hashMapOf("id" to 114, "parent" to 103, "name" to "大里區", "zip" to 412),
        hashMapOf("id" to 115, "parent" to 103, "name" to "霧峰區", "zip" to 413),
        hashMapOf("id" to 116, "parent" to 103, "name" to "烏日區", "zip" to 414),
        hashMapOf("id" to 117, "parent" to 103, "name" to "豐原區", "zip" to 420),
        hashMapOf("id" to 118, "parent" to 103, "name" to "后里區", "zip" to 421),
        hashMapOf("id" to 119, "parent" to 103, "name" to "石岡區", "zip" to 422),
        hashMapOf("id" to 120, "parent" to 103, "name" to "東勢區", "zip" to 423),
        hashMapOf("id" to 121, "parent" to 103, "name" to "和平區", "zip" to 424),
        hashMapOf("id" to 122, "parent" to 103, "name" to "新社區", "zip" to 426),
        hashMapOf("id" to 123, "parent" to 103, "name" to "潭子區", "zip" to 427),
        hashMapOf("id" to 124, "parent" to 103, "name" to "大雅區", "zip" to 428),
        hashMapOf("id" to 125, "parent" to 103, "name" to "神岡區", "zip" to 429),
        hashMapOf("id" to 126, "parent" to 103, "name" to "大肚區", "zip" to 432),
        hashMapOf("id" to 127, "parent" to 103, "name" to "沙鹿區", "zip" to 433),
        hashMapOf("id" to 128, "parent" to 103, "name" to "龍井區", "zip" to 434),
        hashMapOf("id" to 129, "parent" to 103, "name" to "梧棲區", "zip" to 435),
        hashMapOf("id" to 130, "parent" to 103, "name" to "清水區", "zip" to 436),
        hashMapOf("id" to 131, "parent" to 103, "name" to "大甲區", "zip" to 437),
        hashMapOf("id" to 132, "parent" to 103, "name" to "外埔區", "zip" to 438),
        hashMapOf("id" to 133, "parent" to 103, "name" to "大安區", "zip" to 439),
        hashMapOf("id" to 134, "parent" to 0, "name" to "彰化縣", "zip" to 500),
        hashMapOf("id" to 135, "parent" to 134, "name" to "彰化市", "zip" to 500),
        hashMapOf("id" to 136, "parent" to 134, "name" to "芬園鄉", "zip" to 502),
        hashMapOf("id" to 137, "parent" to 134, "name" to "花壇鄉", "zip" to 503),
        hashMapOf("id" to 138, "parent" to 134, "name" to "秀水鄉", "zip" to 504),
        hashMapOf("id" to 139, "parent" to 134, "name" to "鹿港鎮", "zip" to 505),
        hashMapOf("id" to 140, "parent" to 134, "name" to "福興鄉", "zip" to 506),
        hashMapOf("id" to 141, "parent" to 134, "name" to "線西鄉", "zip" to 507),
        hashMapOf("id" to 142, "parent" to 134, "name" to "和美鎮", "zip" to 508),
        hashMapOf("id" to 143, "parent" to 134, "name" to "伸港鄉", "zip" to 509),
        hashMapOf("id" to 144, "parent" to 134, "name" to "員林鎮", "zip" to 510),
        hashMapOf("id" to 145, "parent" to 134, "name" to "社頭鄉", "zip" to 511),
        hashMapOf("id" to 146, "parent" to 134, "name" to "永靖鄉", "zip" to 512),
        hashMapOf("id" to 147, "parent" to 134, "name" to "埔心鄉", "zip" to 513),
        hashMapOf("id" to 148, "parent" to 134, "name" to "溪湖鎮", "zip" to 514),
        hashMapOf("id" to 149, "parent" to 134, "name" to "大村鄉", "zip" to 515),
        hashMapOf("id" to 150, "parent" to 134, "name" to "埔鹽鄉", "zip" to 516),
        hashMapOf("id" to 151, "parent" to 134, "name" to "田中鎮", "zip" to 520),
        hashMapOf("id" to 152, "parent" to 134, "name" to "北斗鎮", "zip" to 521),
        hashMapOf("id" to 153, "parent" to 134, "name" to "田尾鄉", "zip" to 522),
        hashMapOf("id" to 154, "parent" to 134, "name" to "埤頭鄉", "zip" to 523),
        hashMapOf("id" to 155, "parent" to 134, "name" to "溪州鄉", "zip" to 524),
        hashMapOf("id" to 156, "parent" to 134, "name" to "竹塘鄉", "zip" to 525),
        hashMapOf("id" to 157, "parent" to 134, "name" to "二林鎮", "zip" to 526),
        hashMapOf("id" to 158, "parent" to 134, "name" to "大城鄉", "zip" to 527),
        hashMapOf("id" to 159, "parent" to 134, "name" to "芳苑鄉", "zip" to 528),
        hashMapOf("id" to 160, "parent" to 134, "name" to "二水鄉", "zip" to 530),
        hashMapOf("id" to 161, "parent" to 0, "name" to "南投縣", "zip" to 540),
        hashMapOf("id" to 162, "parent" to 161, "name" to "南投市", "zip" to 540),
        hashMapOf("id" to 163, "parent" to 161, "name" to "中寮鄉", "zip" to 541),
        hashMapOf("id" to 164, "parent" to 161, "name" to "草屯鎮", "zip" to 542),
        hashMapOf("id" to 165, "parent" to 161, "name" to "國姓鄉", "zip" to 544),
        hashMapOf("id" to 166, "parent" to 161, "name" to "埔里鎮", "zip" to 545),
        hashMapOf("id" to 167, "parent" to 161, "name" to "仁愛鄉", "zip" to 546),
        hashMapOf("id" to 168, "parent" to 161, "name" to "名間鄉", "zip" to 551),
        hashMapOf("id" to 169, "parent" to 161, "name" to "集集鎮", "zip" to 552),
        hashMapOf("id" to 170, "parent" to 161, "name" to "水里鄉", "zip" to 553),
        hashMapOf("id" to 171, "parent" to 161, "name" to "魚池鄉", "zip" to 555),
        hashMapOf("id" to 172, "parent" to 161, "name" to "信義鄉", "zip" to 556),
        hashMapOf("id" to 173, "parent" to 161, "name" to "竹山鎮", "zip" to 557),
        hashMapOf("id" to 174, "parent" to 161, "name" to "鹿谷鄉", "zip" to 558),
        hashMapOf("id" to 175, "parent" to 0, "name" to "雲林縣", "zip" to 630),
        hashMapOf("id" to 176, "parent" to 175, "name" to "斗南鎮", "zip" to 630),
        hashMapOf("id" to 177, "parent" to 175, "name" to "大埤鄉", "zip" to 631),
        hashMapOf("id" to 178, "parent" to 175, "name" to "虎尾鎮", "zip" to 632),
        hashMapOf("id" to 179, "parent" to 175, "name" to "土庫鎮", "zip" to 633),
        hashMapOf("id" to 180, "parent" to 175, "name" to "褒忠鄉", "zip" to 634),
        hashMapOf("id" to 181, "parent" to 175, "name" to "東勢鄉", "zip" to 635),
        hashMapOf("id" to 182, "parent" to 175, "name" to "台西鄉", "zip" to 636),
        hashMapOf("id" to 183, "parent" to 175, "name" to "崙背鄉", "zip" to 637),
        hashMapOf("id" to 184, "parent" to 175, "name" to "麥寮鄉", "zip" to 638),
        hashMapOf("id" to 185, "parent" to 175, "name" to "斗六市", "zip" to 640),
        hashMapOf("id" to 186, "parent" to 175, "name" to "林內鄉", "zip" to 643),
        hashMapOf("id" to 187, "parent" to 175, "name" to "古坑鄉", "zip" to 646),
        hashMapOf("id" to 188, "parent" to 175, "name" to "莿桐鄉", "zip" to 647),
        hashMapOf("id" to 189, "parent" to 175, "name" to "西螺鎮", "zip" to 648),
        hashMapOf("id" to 190, "parent" to 175, "name" to "二崙鄉", "zip" to 649),
        hashMapOf("id" to 191, "parent" to 175, "name" to "北港鎮", "zip" to 651),
        hashMapOf("id" to 192, "parent" to 175, "name" to "水林鄉", "zip" to 652),
        hashMapOf("id" to 193, "parent" to 175, "name" to "口湖鄉", "zip" to 653),
        hashMapOf("id" to 194, "parent" to 175, "name" to "四湖鄉", "zip" to 654),
        hashMapOf("id" to 195, "parent" to 175, "name" to "元長鄉", "zip" to 655),
        hashMapOf("id" to 196, "parent" to 0, "name" to "嘉義市", "zip" to 600),
        hashMapOf("id" to 197, "parent" to 196, "name" to "東區", "zip" to 600),
        hashMapOf("id" to 198, "parent" to 196, "name" to "西區", "zip" to 600),
        hashMapOf("id" to 199, "parent" to 0, "name" to "嘉義縣", "zip" to 602),
        hashMapOf("id" to 200, "parent" to 199, "name" to "番路鄉", "zip" to 602),
        hashMapOf("id" to 201, "parent" to 199, "name" to "梅山鄉", "zip" to 603),
        hashMapOf("id" to 202, "parent" to 199, "name" to "竹崎鄉", "zip" to 604),
        hashMapOf("id" to 203, "parent" to 199, "name" to "阿里山鄉", "zip" to 605),
        hashMapOf("id" to 204, "parent" to 199, "name" to "中埔鄉", "zip" to 606),
        hashMapOf("id" to 205, "parent" to 199, "name" to "大埔鄉", "zip" to 607),
        hashMapOf("id" to 206, "parent" to 199, "name" to "水上鄉", "zip" to 608),
        hashMapOf("id" to 207, "parent" to 199, "name" to "鹿草鄉", "zip" to 611),
        hashMapOf("id" to 208, "parent" to 199, "name" to "太保市", "zip" to 612),
        hashMapOf("id" to 209, "parent" to 199, "name" to "朴子市", "zip" to 613),
        hashMapOf("id" to 210, "parent" to 199, "name" to "東石鄉", "zip" to 614),
        hashMapOf("id" to 211, "parent" to 199, "name" to "六腳鄉", "zip" to 615),
        hashMapOf("id" to 212, "parent" to 199, "name" to "新港鄉", "zip" to 616),
        hashMapOf("id" to 213, "parent" to 199, "name" to "民雄鄉", "zip" to 621),
        hashMapOf("id" to 214, "parent" to 199, "name" to "大林鎮", "zip" to 622),
        hashMapOf("id" to 215, "parent" to 199, "name" to "溪口鄉", "zip" to 623),
        hashMapOf("id" to 216, "parent" to 199, "name" to "義竹鄉", "zip" to 624),
        hashMapOf("id" to 217, "parent" to 199, "name" to "布袋鎮", "zip" to 625),
        hashMapOf("id" to 218, "parent" to 0, "name" to "台南市", "zip" to 700),
        hashMapOf("id" to 219, "parent" to 218, "name" to "中西區", "zip" to 700),
        hashMapOf("id" to 220, "parent" to 218, "name" to "東區", "zip" to 701),
        hashMapOf("id" to 221, "parent" to 218, "name" to "南區", "zip" to 702),
        hashMapOf("id" to 222, "parent" to 218, "name" to "北區", "zip" to 704),
        hashMapOf("id" to 223, "parent" to 218, "name" to "安平區", "zip" to 708),
        hashMapOf("id" to 224, "parent" to 218, "name" to "安南區", "zip" to 709),
        hashMapOf("id" to 226, "parent" to 218, "name" to "永康區", "zip" to 710),
        hashMapOf("id" to 227, "parent" to 218, "name" to "歸仁區", "zip" to 711),
        hashMapOf("id" to 228, "parent" to 218, "name" to "新化區", "zip" to 712),
        hashMapOf("id" to 229, "parent" to 218, "name" to "左鎮區", "zip" to 713),
        hashMapOf("id" to 230, "parent" to 218, "name" to "玉井區", "zip" to 714),
        hashMapOf("id" to 231, "parent" to 218, "name" to "楠西區", "zip" to 715),
        hashMapOf("id" to 232, "parent" to 218, "name" to "南化區", "zip" to 716),
        hashMapOf("id" to 233, "parent" to 218, "name" to "仁德區", "zip" to 717),
        hashMapOf("id" to 234, "parent" to 218, "name" to "關廟區", "zip" to 718),
        hashMapOf("id" to 235, "parent" to 218, "name" to "龍崎區", "zip" to 719),
        hashMapOf("id" to 236, "parent" to 218, "name" to "官田區", "zip" to 720),
        hashMapOf("id" to 237, "parent" to 218, "name" to "麻豆區", "zip" to 721),
        hashMapOf("id" to 238, "parent" to 218, "name" to "佳里區", "zip" to 722),
        hashMapOf("id" to 239, "parent" to 218, "name" to "西港區", "zip" to 723),
        hashMapOf("id" to 240, "parent" to 218, "name" to "七股區", "zip" to 724),
        hashMapOf("id" to 241, "parent" to 218, "name" to "將軍區", "zip" to 725),
        hashMapOf("id" to 242, "parent" to 218, "name" to "學甲區", "zip" to 726),
        hashMapOf("id" to 243, "parent" to 218, "name" to "北門區", "zip" to 727),
        hashMapOf("id" to 244, "parent" to 218, "name" to "新營區", "zip" to 730),
        hashMapOf("id" to 245, "parent" to 218, "name" to "後壁區", "zip" to 731),
        hashMapOf("id" to 246, "parent" to 218, "name" to "白河區", "zip" to 732),
        hashMapOf("id" to 247, "parent" to 218, "name" to "東山區", "zip" to 733),
        hashMapOf("id" to 248, "parent" to 218, "name" to "六甲區", "zip" to 734),
        hashMapOf("id" to 249, "parent" to 218, "name" to "下營區", "zip" to 735),
        hashMapOf("id" to 250, "parent" to 218, "name" to "柳營區", "zip" to 736),
        hashMapOf("id" to 251, "parent" to 218, "name" to "鹽水區", "zip" to 737),
        hashMapOf("id" to 252, "parent" to 218, "name" to "善化區", "zip" to 741),
        hashMapOf("id" to 253, "parent" to 218, "name" to "大內區", "zip" to 742),
        hashMapOf("id" to 254, "parent" to 218, "name" to "山上區", "zip" to 743),
        hashMapOf("id" to 255, "parent" to 218, "name" to "新市區", "zip" to 744),
        hashMapOf("id" to 256, "parent" to 218, "name" to "安定區", "zip" to 745),
        hashMapOf("id" to 257, "parent" to 0, "name" to "高雄市", "zip" to 800),
        hashMapOf("id" to 258, "parent" to 257, "name" to "新興區", "zip" to 800),
        hashMapOf("id" to 259, "parent" to 257, "name" to "前金區", "zip" to 801),
        hashMapOf("id" to 260, "parent" to 257, "name" to "苓雅區", "zip" to 802),
        hashMapOf("id" to 261, "parent" to 257, "name" to "鹽埕區", "zip" to 803),
        hashMapOf("id" to 262, "parent" to 257, "name" to "鼓山區", "zip" to 804),
        hashMapOf("id" to 263, "parent" to 257, "name" to "旗津區", "zip" to 805),
        hashMapOf("id" to 264, "parent" to 257, "name" to "前鎮區", "zip" to 806),
        hashMapOf("id" to 265, "parent" to 257, "name" to "三民區", "zip" to 807),
        hashMapOf("id" to 266, "parent" to 257, "name" to "楠梓區", "zip" to 811),
        hashMapOf("id" to 267, "parent" to 257, "name" to "小港區", "zip" to 812),
        hashMapOf("id" to 268, "parent" to 257, "name" to "左營區", "zip" to 813),
        hashMapOf("id" to 270, "parent" to 257, "name" to "仁武區", "zip" to 814),
        hashMapOf("id" to 271, "parent" to 257, "name" to "大社區", "zip" to 815),
        hashMapOf("id" to 272, "parent" to 257, "name" to "岡山區", "zip" to 820),
        hashMapOf("id" to 273, "parent" to 257, "name" to "路竹區", "zip" to 821),
        hashMapOf("id" to 274, "parent" to 257, "name" to "阿蓮區", "zip" to 822),
        hashMapOf("id" to 275, "parent" to 257, "name" to "田寮區", "zip" to 823),
        hashMapOf("id" to 276, "parent" to 257, "name" to "燕巢區", "zip" to 824),
        hashMapOf("id" to 277, "parent" to 257, "name" to "橋頭區", "zip" to 825),
        hashMapOf("id" to 278, "parent" to 257, "name" to "梓官區", "zip" to 826),
        hashMapOf("id" to 279, "parent" to 257, "name" to "彌陀區", "zip" to 827),
        hashMapOf("id" to 280, "parent" to 257, "name" to "永安區", "zip" to 828),
        hashMapOf("id" to 281, "parent" to 257, "name" to "湖內區", "zip" to 829),
        hashMapOf("id" to 282, "parent" to 257, "name" to "鳳山區", "zip" to 830),
        hashMapOf("id" to 283, "parent" to 257, "name" to "大寮區", "zip" to 831),
        hashMapOf("id" to 284, "parent" to 257, "name" to "林園區", "zip" to 832),
        hashMapOf("id" to 285, "parent" to 257, "name" to "鳥松區", "zip" to 833),
        hashMapOf("id" to 286, "parent" to 257, "name" to "大樹區", "zip" to 840),
        hashMapOf("id" to 287, "parent" to 257, "name" to "旗山區", "zip" to 842),
        hashMapOf("id" to 288, "parent" to 257, "name" to "美濃區", "zip" to 843),
        hashMapOf("id" to 289, "parent" to 257, "name" to "六龜區", "zip" to 844),
        hashMapOf("id" to 290, "parent" to 257, "name" to "內門區", "zip" to 845),
        hashMapOf("id" to 291, "parent" to 257, "name" to "杉林區", "zip" to 846),
        hashMapOf("id" to 292, "parent" to 257, "name" to "甲仙區", "zip" to 847),
        hashMapOf("id" to 293, "parent" to 257, "name" to "桃源區", "zip" to 848),
        hashMapOf("id" to 294, "parent" to 257, "name" to "那瑪夏區", "zip" to 849),
        hashMapOf("id" to 295, "parent" to 257, "name" to "茂林區", "zip" to 851),
        hashMapOf("id" to 296, "parent" to 257, "name" to "茄萣區", "zip" to 852),
        hashMapOf("id" to 297, "parent" to 0, "name" to "屏東縣", "zip" to 900),
        hashMapOf("id" to 298, "parent" to 297, "name" to "屏東市", "zip" to 900),
        hashMapOf("id" to 299, "parent" to 297, "name" to "三地門鄉", "zip" to 901),
        hashMapOf("id" to 300, "parent" to 297, "name" to "霧台鄉", "zip" to 902),
        hashMapOf("id" to 301, "parent" to 297, "name" to "瑪家鄉", "zip" to 903),
        hashMapOf("id" to 302, "parent" to 297, "name" to "九如鄉", "zip" to 904),
        hashMapOf("id" to 303, "parent" to 297, "name" to "里港鄉", "zip" to 905),
        hashMapOf("id" to 304, "parent" to 297, "name" to "高樹鄉", "zip" to 906),
        hashMapOf("id" to 305, "parent" to 297, "name" to "鹽埔鄉", "zip" to 907),
        hashMapOf("id" to 306, "parent" to 297, "name" to "長治鄉", "zip" to 908),
        hashMapOf("id" to 307, "parent" to 297, "name" to "麟洛鄉", "zip" to 909),
        hashMapOf("id" to 308, "parent" to 297, "name" to "竹田鄉", "zip" to 911),
        hashMapOf("id" to 309, "parent" to 297, "name" to "內埔鄉", "zip" to 912),
        hashMapOf("id" to 310, "parent" to 297, "name" to "萬丹鄉", "zip" to 913),
        hashMapOf("id" to 311, "parent" to 297, "name" to "潮州鎮", "zip" to 920),
        hashMapOf("id" to 312, "parent" to 297, "name" to "泰武鄉", "zip" to 921),
        hashMapOf("id" to 313, "parent" to 297, "name" to "來義鄉", "zip" to 922),
        hashMapOf("id" to 314, "parent" to 297, "name" to "萬巒鄉", "zip" to 923),
        hashMapOf("id" to 315, "parent" to 297, "name" to "崁頂鄉", "zip" to 924),
        hashMapOf("id" to 316, "parent" to 297, "name" to "新埤鄉", "zip" to 925),
        hashMapOf("id" to 317, "parent" to 297, "name" to "南州鄉", "zip" to 926),
        hashMapOf("id" to 318, "parent" to 297, "name" to "林邊鄉", "zip" to 927),
        hashMapOf("id" to 319, "parent" to 297, "name" to "東港鎮", "zip" to 928),
        hashMapOf("id" to 320, "parent" to 297, "name" to "琉球鄉", "zip" to 929),
        hashMapOf("id" to 321, "parent" to 297, "name" to "佳冬鄉", "zip" to 931),
        hashMapOf("id" to 322, "parent" to 297, "name" to "新園鄉", "zip" to 932),
        hashMapOf("id" to 323, "parent" to 297, "name" to "枋寮鄉", "zip" to 940),
        hashMapOf("id" to 324, "parent" to 297, "name" to "枋山鄉", "zip" to 941),
        hashMapOf("id" to 325, "parent" to 297, "name" to "春日鄉", "zip" to 942),
        hashMapOf("id" to 326, "parent" to 297, "name" to "獅子鄉", "zip" to 943),
        hashMapOf("id" to 327, "parent" to 297, "name" to "車城鄉", "zip" to 944),
        hashMapOf("id" to 328, "parent" to 297, "name" to "牡丹鄉", "zip" to 945),
        hashMapOf("id" to 329, "parent" to 297, "name" to "恆春鎮", "zip" to 946),
        hashMapOf("id" to 330, "parent" to 297, "name" to "滿洲鄉", "zip" to 947),
        hashMapOf("id" to 331, "parent" to 0, "name" to "台東縣", "zip" to 950),
        hashMapOf("id" to 332, "parent" to 331, "name" to "台東市", "zip" to 950),
        hashMapOf("id" to 333, "parent" to 331, "name" to "綠島鄉", "zip" to 951),
        hashMapOf("id" to 334, "parent" to 331, "name" to "蘭嶼鄉", "zip" to 952),
        hashMapOf("id" to 335, "parent" to 331, "name" to "延平鄉", "zip" to 953),
        hashMapOf("id" to 336, "parent" to 331, "name" to "卑南鄉", "zip" to 954),
        hashMapOf("id" to 337, "parent" to 331, "name" to "鹿野鄉", "zip" to 955),
        hashMapOf("id" to 338, "parent" to 331, "name" to "關山鎮", "zip" to 956),
        hashMapOf("id" to 339, "parent" to 331, "name" to "海端鄉", "zip" to 957),
        hashMapOf("id" to 340, "parent" to 331, "name" to "池上鄉", "zip" to 958),
        hashMapOf("id" to 341, "parent" to 331, "name" to "東河鄉", "zip" to 959),
        hashMapOf("id" to 342, "parent" to 331, "name" to "成功鎮", "zip" to 961),
        hashMapOf("id" to 343, "parent" to 331, "name" to "長濱鄉", "zip" to 962),
        hashMapOf("id" to 344, "parent" to 331, "name" to "太麻里鄉", "zip" to 963),
        hashMapOf("id" to 345, "parent" to 331, "name" to "金峰鄉", "zip" to 964),
        hashMapOf("id" to 346, "parent" to 331, "name" to "大武鄉", "zip" to 965),
        hashMapOf("id" to 347, "parent" to 331, "name" to "達仁鄉", "zip" to 966),
        hashMapOf("id" to 348, "parent" to 0, "name" to "花蓮縣", "zip" to 970),
        hashMapOf("id" to 349, "parent" to 348, "name" to "花蓮市", "zip" to 970),
        hashMapOf("id" to 350, "parent" to 348, "name" to "新城鄉", "zip" to 971),
        hashMapOf("id" to 351, "parent" to 348, "name" to "秀林鄉", "zip" to 972),
        hashMapOf("id" to 352, "parent" to 348, "name" to "吉安鄉", "zip" to 973),
        hashMapOf("id" to 353, "parent" to 348, "name" to "壽豐鄉", "zip" to 974),
        hashMapOf("id" to 354, "parent" to 348, "name" to "鳳林鎮", "zip" to 975),
        hashMapOf("id" to 355, "parent" to 348, "name" to "光復鄉", "zip" to 976),
        hashMapOf("id" to 356, "parent" to 348, "name" to "豐濱鄉", "zip" to 977),
        hashMapOf("id" to 357, "parent" to 348, "name" to "瑞穗鄉", "zip" to 978),
        hashMapOf("id" to 358, "parent" to 348, "name" to "萬榮鄉", "zip" to 979),
        hashMapOf("id" to 359, "parent" to 348, "name" to "玉里鎮", "zip" to 981),
        hashMapOf("id" to 360, "parent" to 348, "name" to "卓溪鄉", "zip" to 982),
        hashMapOf("id" to 361, "parent" to 348, "name" to "富里鄉", "zip" to 983),
        hashMapOf("id" to 362, "parent" to 0, "name" to "宜蘭縣", "zip" to 260),
        hashMapOf("id" to 363, "parent" to 362, "name" to "宜蘭市", "zip" to 260),
        hashMapOf("id" to 364, "parent" to 362, "name" to "頭城鎮", "zip" to 261),
        hashMapOf("id" to 365, "parent" to 362, "name" to "礁溪鄉", "zip" to 262),
        hashMapOf("id" to 366, "parent" to 362, "name" to "壯圍鄉", "zip" to 263),
        hashMapOf("id" to 367, "parent" to 362, "name" to "員山鄉", "zip" to 264),
        hashMapOf("id" to 368, "parent" to 362, "name" to "羅東鎮", "zip" to 265),
        hashMapOf("id" to 369, "parent" to 362, "name" to "三星鄉", "zip" to 266),
        hashMapOf("id" to 370, "parent" to 362, "name" to "大同鄉", "zip" to 267),
        hashMapOf("id" to 371, "parent" to 362, "name" to "五結鄉", "zip" to 268),
        hashMapOf("id" to 372, "parent" to 362, "name" to "冬山鄉", "zip" to 269),
        hashMapOf("id" to 373, "parent" to 362, "name" to "蘇澳鎮", "zip" to 270),
        hashMapOf("id" to 374, "parent" to 362, "name" to "南澳鄉", "zip" to 272),
        hashMapOf("id" to 375, "parent" to 0, "name" to "澎湖縣", "zip" to 880),
        hashMapOf("id" to 376, "parent" to 375, "name" to "馬公市", "zip" to 880),
        hashMapOf("id" to 377, "parent" to 375, "name" to "西嶼鄉", "zip" to 881),
        hashMapOf("id" to 378, "parent" to 375, "name" to "望安鄉", "zip" to 882),
        hashMapOf("id" to 379, "parent" to 375, "name" to "七美鄉", "zip" to 883),
        hashMapOf("id" to 380, "parent" to 375, "name" to "白沙鄉", "zip" to 884),
        hashMapOf("id" to 381, "parent" to 375, "name" to "湖西鄉", "zip" to 885),
        hashMapOf("id" to 382, "parent" to 0, "name" to "金門縣", "zip" to 890),
        hashMapOf("id" to 383, "parent" to 382, "name" to "金沙鎮", "zip" to 890),
        hashMapOf("id" to 384, "parent" to 382, "name" to "金湖鎮", "zip" to 891),
        hashMapOf("id" to 385, "parent" to 382, "name" to "金寧鄉", "zip" to 892),
        hashMapOf("id" to 386, "parent" to 382, "name" to "金城鎮", "zip" to 893),
        hashMapOf("id" to 387, "parent" to 382, "name" to "烈嶼鄉", "zip" to 894),
        hashMapOf("id" to 388, "parent" to 382, "name" to "烏坵鄉", "zip" to 896),
        hashMapOf("id" to 389, "parent" to 0, "name" to "連江縣", "zip" to 209),
        hashMapOf("id" to 390, "parent" to 389, "name" to "南竿鄉", "zip" to 209),
        hashMapOf("id" to 391, "parent" to 389, "name" to "北竿鄉", "zip" to 210),
        hashMapOf("id" to 392, "parent" to 389, "name" to "莒光鄉", "zip" to 211),
        hashMapOf("id" to 393, "parent" to 389, "name" to "東引鄉", "zip" to 212),
        hashMapOf("id" to 395, "parent" to 0, "name" to "全省", "zip" to 0)
    )

    fun getAreasByCityID(city_id: Int): ArrayList<Area> {

        val areas: ArrayList<Area> = arrayListOf()
        for (zone in Zones.zones) {
            if (zone.containsKey("parent")) {
                val parent: Int = zone["parent"] as Int
                if (parent == city_id) {
                    var id: Int? = null
                    var name: String? = null
                    if (zone.containsKey("id")) {
                        id = zone["id"] as Int
                    }
                    if (zone.containsKey("name")) {
                        name = zone["name"] as String
                    }
                    if (id != null && name != null) {
                        val area = Area(id, name)
                        areas.add(area)
                    }
                }
            }
        }

        return areas
    }

    fun getCitys(): ArrayList<City> {

        val citys: ArrayList<City> = arrayListOf()
        for (zone in Zones.zones) {
            if (zone.containsKey("parent")) {
                val parent: Int = zone["parent"] as Int
                if (parent == 0) {
                    var id: Int? = null
                    var name: String? = null
                    if (zone.containsKey("id")) {
                        id = zone["id"] as Int
                    }
                    if (zone.containsKey("name")) {
                        name = zone["name"] as String
                    }
                    if (id != null && name != null) {
                        val city = City(id, name)
                        citys.add(city)
                    }
                }
            }
        }

        return citys
    }

    fun zoneIDToName(zone_id: Int): String {
        var value = ""
        for (zone in Zones.zones) {
            val id = zone["id"] as Int
            if (id == zone_id) {
                value = zone["name"] as String
                break
            }
        }
        return value
    }
}