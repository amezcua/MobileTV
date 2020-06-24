package net.byteabyte.mobiletv.data.network.parsing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.byteabyte.mobiletv.data.network.retrofit.top_rated.JsonTopRatedShowsResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TopRatedParsingTest {

    @Test
    fun `Top rated responses can be parsed into paged objects`() {
        val adapter = buildMoshiAdapter()
        val json1 = adapter.fromJson(sample1)!!
        val page1 = json1.toNetworkResponsePage()

        assertEquals(1, page1.page)
        assertEquals(20, page1.shows.size)
        assertEquals(990, page1.totalResults)
        assertEquals(50, page1.totalPages)

        val json2 = adapter.fromJson(sample2)!!
        val page2 = json2.toNetworkResponsePage()

        assertEquals(10, page2.page)
        assertEquals(20, page2.shows.size)
        assertEquals(747, page2.totalResults)
        assertEquals(38, page2.totalPages)
    }

    private fun buildMoshiAdapter() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(JsonTopRatedShowsResult::class.java)

    private val sample1 = """
        {"page":1,"total_results":990,"total_pages":50,"results":[{"original_name":"I Am Not an Animal","genre_ids":[16,35],"name":"I Am Not an Animal","popularity":11.782,"origin_country":["GB"],"vote_count":474,"first_air_date":"2004-05-10","backdrop_path":null,"original_language":"en","id":100,"vote_average":9.4,"overview":"I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.","poster_path":"\/qG59J1Q7rpBc1dvku4azbzcqo8h.jpg"},{"original_name":"約束のネバーランド","genre_ids":[16,18,9648,10759,10765],"name":"The Promised Neverland","popularity":11.946,"origin_country":["JP"],"vote_count":149,"first_air_date":"2019-01-09","backdrop_path":"\/uAjMQlbPkVHmUahhCouANlHSDW2.jpg","original_language":"ja","id":83097,"vote_average":9.2,"overview":"The one adored as the mother is not the real parent.  The people living here together are not actual siblings. The Gracefield House is where orphaned children live. An irreplaceable home where 38 siblings and Mom live happy lives, even with no blood relations.  However, their everyday life suddenly came to an abrupt end one day...","poster_path":"\/1sKCm6vJbDq64zcfHNOY67ltLns.jpg"},{"original_name":"かぐや様は告らせたい～天才たちの恋愛頭脳戦～","genre_ids":[16,35,18],"name":"Kaguya-sama: Love is War","popularity":17.642,"origin_country":["JP"],"vote_count":103,"first_air_date":"2019-01-12","backdrop_path":"\/dJ8yrSokdTMnhKJw06MllSfCegb.jpg","original_language":"ja","id":83121,"vote_average":9,"overview":"Considered a genius due to having the highest grades in the country, Miyuki Shirogane leads the prestigious Shuchiin Academy’s student council as its president, working alongside the beautiful and wealthy vice president Kaguya Shinomiya. The two are often regarded as the perfect couple by students despite them not being in any sort of romantic relationship.\n\nHowever, the truth is that after spending so much time together, the two have developed feelings for one another; unfortunately, neither is willing to confess, as doing so would be a sign of weakness. With their pride as elite students on the line, Miyuki and Kaguya embark on a quest to do whatever is necessary to get a confession out of the other. Through their daily antics, the battle of love begins!","poster_path":"\/td5dHTdzEDGmvyWEhlYMPHDTlBz.jpg"},{"original_name":"On My Block","id":76747,"name":"On My Block","popularity":13.501,"vote_count":126,"vote_average":8.9,"first_air_date":"2018-03-16","poster_path":"\/aFHmGVDNLFmhLjDNbFuMSBOxUv5.jpg","genre_ids":[35],"original_language":"en","backdrop_path":"\/jt7c70Axkhy7o9u00JT8Lygfd3H.jpg","overview":"A coming of age comedy following a diverse group of teenage friends as they confront the challenges of growing up in gritty inner-city Los Angeles.","origin_country":["US"]},{"original_name":"はじめの一歩","id":42705,"name":"Fighting Spirit","popularity":20.283,"vote_count":165,"vote_average":8.9,"first_air_date":"2000-10-03","poster_path":"\/1LApB9C9kEkh2ZU2vzAhurNDipl.jpg","genre_ids":[16,35,18],"original_language":"ja","backdrop_path":"\/2w8FaLwwJTWr6ExUMeVgT2Th5YT.jpg","overview":"Ippo, the son of a fishing boat tour owner, takes up boxing to increase his confidence. Through intense training and willpower Ippo claims the Japanese featherweight  title.","origin_country":["JP"]},{"original_name":"スラムダンク","genre_ids":[16,35],"name":"Slam Dunk","popularity":55.564,"origin_country":["JP"],"vote_count":170,"first_air_date":"1993-10-16","backdrop_path":"\/AvHqemLogpyR2Bg4yTlT1DPzrH.jpg","original_language":"ja","id":42573,"vote_average":8.9,"overview":"Sakuragi Hanamichi is a junior high punk used to getting into fights and being rejected by girls but upon entering high school he meets the girl of his dreams, Haruko Akagi. He will do anything in order to win her heart including joining the school basketball team that is aiming tor. The problem is that Sakuragi has never played basketball before and a freshman sensation is stealing the spotlight and Haruko's affection from him.","poster_path":"\/65Xo6gKpREqXpzliLx9PYqSQ4O.jpg"},{"original_name":"神之塔","id":97860,"name":"Tower of God","popularity":13.64,"vote_count":102,"vote_average":8.9,"first_air_date":"2020-04-01","poster_path":"\/fUs9KMgEJj3wYJGa75MtPSdyJXk.jpg","genre_ids":[16,18,9648,10759,10765],"original_language":"ja","backdrop_path":"\/bkvkJyLqOk2ZbELDEukEAXEatrt.jpg","overview":"Tower of God centers around a boy called Twenty-Fifth Bam, who has spent most of his life trapped beneath a vast and mysterious Tower, with only his close friend, Rachel, to keep him company. When Rachel enters the Tower, Bam manages to open the door into it as well, and faces challenges at each floor of this tower as he tries to find his closest companion.","origin_country":["JP"]},{"original_name":"盾の勇者の成り上がり","genre_ids":[16,18,10759,10765],"name":"The Rising of the Shield Hero","popularity":7.51,"origin_country":["JP"],"vote_count":172,"first_air_date":"2019-01-09","backdrop_path":"\/qSgBzXdu6QwVVeqOYOlHolkLRxZ.jpg","original_language":"ja","id":83095,"vote_average":8.9,"overview":"Iwatani Naofumi was summoned into a parallel world along with 3 other people to become the world's Heroes. Each of the heroes respectively equipped with their own legendary equipment when summoned, Naofumi received the Legendary Shield as his weapon. Due to Naofumi's lack of charisma and experience he's labeled as the weakest, only to end up betrayed, falsely accused, and robbed by on the third day of adventure. Shunned by everyone from the king to peasants, Naofumi's thoughts were filled with nothing but vengeance and hatred. Thus, his destiny in a parallel World begins...","poster_path":"\/ftavpq2PJn5pyo5opJEmj8QleKD.jpg"},{"original_name":"Anne with an E","genre_ids":[18,10751],"name":"Anne with an E","popularity":35.291,"origin_country":["CA"],"vote_count":503,"first_air_date":"2017-03-19","backdrop_path":"\/ywQtHG17LZhAFZyZtBflhtFMtJ7.jpg","original_language":"en","id":70785,"vote_average":8.8,"overview":"A coming-of-age story about an outsider who, against all odds and numerous challenges, fights for love and acceptance and for her place in the world. The series centers on a young orphaned girl in the late 1890’s, who, after an abusive childhood spent in orphanages and the homes of strangers, is mistakenly sent to live with an elderly woman and her aging brother. Over time, 13-year-old Anne will transform their lives and eventually the small town in which they live with her unique spirit, fierce intellect and brilliant imagination.","poster_path":"\/6P6tXhjT5tK3qOXzxF9OMLlG7iz.jpg"},{"original_name":"Shadow Hunter","genre_ids":[99],"name":"Shadow Hunter","popularity":10.708,"origin_country":["CA"],"vote_count":173,"first_air_date":"2016-01-12","backdrop_path":"\/8PRgxgKASPIICbV7OW3KNuUsEid.jpg","original_language":"en","id":12313,"vote_average":8.8,"overview":"Shadow Hunter is a 13-episode documentary television series about the paranormal, hosted by Darryll Walsh, a ghost hunter, best-selling author and doctor of parapsychology.\n\n\"Cases\" presented in the series are from Walsh's own collection, news headlines, the Internet, and parapsychological organizations that Walsh speaks to.\n\nThe series is produced in association with CHUM Television, distributed by Picture Box Distribution and airs as of 2005 on Space in Canada.","poster_path":"\/anPnkQLZGWBKLwqH32i057QdpOH.jpg"},{"original_name":"鬼滅の刃","genre_ids":[16,18,10759,10765],"name":"Demon Slayer: Kimetsu no Yaiba","popularity":4.18,"origin_country":["JP"],"vote_count":594,"first_air_date":"2019-04-06","backdrop_path":"\/nTvM4mhqNlHIvUkI1gVnW6XP7GG.jpg","original_language":"ja","id":85937,"vote_average":8.8,"overview":"It is the Taisho Period in Japan. Tanjiro, a kindhearted boy who sells charcoal for a living, finds his family slaughtered by a demon. To make matters worse, his younger sister Nezuko, the sole survivor, has been transformed into a demon herself. Though devastated by this grim reality, Tanjiro resolves to become a “demon slayer” so that he can turn his sister back into a human, and kill the demon that massacred his family.","poster_path":"\/wrCVHdkBlBWdJUZPvnJWcBRuhSY.jpg"},{"original_name":"なんでここに先生が!?","genre_ids":[16,35],"name":"Why the Hell are You Here, Teacher!?","popularity":16.78,"origin_country":["JP"],"vote_count":138,"first_air_date":"2019-04-08","backdrop_path":"\/tRtkUNrF8LXcVZap3LGXMChjVvm.jpg","original_language":"ja","id":86836,"vote_average":8.8,"overview":"Ichiro Sato is about as average as a student can get… except for his above-average ability to land himself in totally awkward, intensely risqué situations with his no-nonsense teacher, Kana Kojima! Ichiro has his hands full dealing with these steamy shenanigans and unexpected encounters in the most unlikely places. At least it can’t get any worse, right?","poster_path":"\/1yss3gl6mMNT9Txvdtvnu2KTWt9.jpg"},{"original_name":"BEASTARS","id":90937,"name":"Beastars","popularity":16.19,"vote_count":194,"vote_average":8.8,"first_air_date":"2019-10-10","poster_path":"\/zKEFedE4KEUWdfPvjVC9G1VyEoK.jpg","genre_ids":[16,18],"original_language":"ja","backdrop_path":"\/pEBbF40kTaJTv1VjG8NW5e1ajw3.jpg","overview":"In a world populated by anthropomorphic animals, herbivores and carnivores coexist with each other. For the adolescences of Cherryton Academy, school life is filled with hope, romance, distrust, and uneasiness.\n\nThe main character is Legoshi the wolf, a member of the drama club. Despite his menacing appearance, he has a very gentle heart. Throughout most of his life, he has always been an object of fear and hatred by other animals, and he's been quite accustomed to that lifestyle. But soon, he finds himself becoming more involved with his fellow classmates who have their own share of insecurities and finds his life in school changing slowly.","origin_country":["JP"]},{"original_name":"３Ｄ彼女　リアルガール","genre_ids":[16],"name":"Real Girl","popularity":12.029,"origin_country":["JP"],"vote_count":156,"first_air_date":"2018-04-04","backdrop_path":"\/jwAUfgwFEzMYUh3lUht8VBB1SU7.jpg","original_language":"ja","id":77721,"vote_average":8.7,"overview":"Tsutsui Hikari (a.k.a \"Tsuttsun\") is a high school student who is content with his virtual life of anime and video games. One day, he gets stuck cleaning the pool with Iroha, a real live girl who is stylish, sassy and known to be easy with boys ... and she aggressively approaches him! Tsuttsun, who has few friends and lives in his own world, finds himself smitten by the confident and wild Iroha ... and his whole world is turned upside down!! It's the awkward and pure love story of a boy who experiences relationship for the first time.","poster_path":"\/3UmNynM1YXkYylyontGXPHncjOC.jpg"},{"original_name":"Rick and Morty","genre_ids":[16,35,10759,10765],"name":"Rick and Morty","popularity":70.414,"origin_country":["US"],"vote_count":2720,"first_air_date":"2013-12-02","backdrop_path":"\/eV3XnUul4UfIivz3kxgeIozeo50.jpg","original_language":"en","id":60625,"vote_average":8.7,"overview":"Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.","poster_path":"\/8kOWDBK6XlPUzckuHDo3wwVRFwt.jpg"},{"original_name":"ドクターストーン","genre_ids":[16,10759],"name":"Dr. Stone","popularity":2.983,"origin_country":["JP"],"vote_count":228,"first_air_date":"2019-07-05","backdrop_path":"\/1Ep6YHL5QcrNC1JN6RYalWRPopi.jpg","original_language":"ja","id":86031,"vote_average":8.7,"overview":"One fateful day, all of humanity was petrified by a blinding flash of light. After several millennia ju awakens and finds himself lost in a world of statues. However, he's not alone! His science-loving friend Senku's been up and running for a few months and he's got a grand plan in mind, to kickstart civilization with the power of science!","poster_path":"\/dLlnzbDCblBXcJqFLXyvN43NIwp.jpg"},{"original_name":"四月は君の嘘","genre_ids":[16,35,18],"name":"Your Lie in April","popularity":15.257,"origin_country":["JP"],"vote_count":155,"first_air_date":"2014-10-10","backdrop_path":"\/iJlztXhWfHZH0VVGPMMl5oZS1RJ.jpg","original_language":"ja","id":61663,"vote_average":8.7,"overview":"Kousei Arima was a genius pianist until his mother's sudden death took away his ability to play. Each day was dull for Kousei. But, then he meets a violinist named Kaori Miyazono who has an eccentric playing style. Can the heartfelt sounds of the girl's violin lead the boy to play the piano again?","poster_path":"\/nksFLYTydth9OYVpMuMbtOBkvMO.jpg"},{"original_name":"Big Time Rush","genre_ids":[35],"name":"Big Time Rush","popularity":17.036,"origin_country":["US"],"vote_count":571,"first_air_date":"2009-11-28","backdrop_path":"\/fXME4R2kk306okBGnFQDttTdKdk.jpg","original_language":"en","id":31356,"vote_average":8.7,"overview":"Four teenage friends move from Minneapolis to Los Angeles to form a potential chart-topping boy band after Kendall is inadvertently discovered by an eccentric record executive, Gustavo Rocque. As they seize this opportunity of a lifetime, these friends embark on an exciting comedy and music-filled journey to prove to themselves and their record label that they are serious about their new career choice.","poster_path":"\/5yzb0iWXilLpg3iz1LT3H3UGBYs.jpg"},{"original_name":"この素晴らしい世界に祝福を！","id":65844,"name":"KonoSuba – God’s blessing on this wonderful world!!","popularity":16.377,"vote_count":173,"vote_average":8.7,"first_air_date":"2016-01-14","poster_path":"\/vtEDHLtl2yNOTRVK8jFJzVQAKHF.jpg","genre_ids":[10759,16,35,10765],"original_language":"ja","backdrop_path":"\/rS7x1T2ZO57T61nKKgLoRUT0bw2.jpg","overview":"After a traffic accident, Kazuma Sato’s  disappointingly brief life was supposed to be over, but he wakes up to  see a beautiful girl before him. She claims to be a goddess, Aqua, and  asks if he would like to go to another world and bring only one thing  with him.\n\nKazuma decides to bring the goddess herself, and they are  transported to a fantasy world filled with adventure, ruled by a demon  king. Now Kazuma only wants to live in peace, but Aqua wants to solve  many of this world’s problems, and the demon king will only turn a blind  eye for so long…","origin_country":["JP"]},{"original_name":"どろろ","id":83100,"name":"Dororo","popularity":18.412,"vote_count":336,"vote_average":8.6,"first_air_date":"2019-01-07","poster_path":"\/jyFHtqYeVvc2A9nLhFGvM49xGPg.jpg","genre_ids":[10759,16],"original_language":"ja","backdrop_path":"\/xXY9WxE3KxwxftTmJIU8LwY5ojD.jpg","overview":"A samurai lord has bartered away his newborn son's organs to forty-eight demons in exchange for dominance on the battlefield. Yet, the abandoned infant survives thanks to a medicine man who equips him with primitive prosthetics—lethal ones with which the wronged son will use to hunt down the multitude of demons to reclaim his body one piece at a time, before confronting his father. On his journeys the young hero encounters an orphan who claims to be the greatest thief in Japan.","origin_country":["JP"]}]}
    """.trimIndent()

    private val sample2 = """
    {
  "page": 10,
  "results": [
    {
      "poster_path": "/tfdiVvJkYMbUOXDWibPjzu5dY6S.jpg",
      "popularity": 1.722162,
      "id": 604,
      "backdrop_path": "/hHwEptckXUwZM7XO2lxZ8w8upuU.jpg",
      "vote_average": 8.17,
      "overview": "Teen Titans is an American animated television series based on the DC Comics characters of the same name, primarily the run of stories by Marv Wolfman and George Pérez in the early-1980s The New Teen Titans comic book series. The show was created by Glen Murakami, developed by David Slack, and produced by Warner Bros. Animation. It premiered on Cartoon Network on July 19, 2003 with the episode \"Divide and Conquer\" and the final episode \"Things Change\" aired on January 16, 2006, with the film Teen Titans: Trouble in Tokyo serving as the series finale. A comic book series, Teen Titans Go!, was based on the TV series. On June 8, 2012, it was announced that the series would be revived as Teen Titans Go! in April 23, 2013 and air on the DC Nation block.IT now airs on the Boomerang channel. ",
      "first_air_date": "2003-07-19",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        16,
        10759
      ],
      "original_language": "en",
      "vote_count": 12,
      "name": "Teen Titans",
      "original_name": "Teen Titans"
    },
    {
      "poster_path": "/utOLkQhxuhwN3PN0UEPnfhJnkrf.jpg",
      "popularity": 1.530522,
      "id": 2085,
      "backdrop_path": "/mzpeRh7Wu9mP3s9EdsbNMaGsykP.jpg",
      "vote_average": 8.16,
      "overview": "Courage the Cowardly Dog is an American comedy horror animated television series created by John R. Dilworth for Cartoon Network. Its central plot revolves around a somewhat anthropomorphic pink/purple dog named Courage who lives with his owners, Muriel and Eustace Bagge, an elderly, married farming couple in the \"Middle of Nowhere\". Courage and his owners are frequently thrown into bizarre misadventures, often involving the paranormal/supernatural and various villains. The show is known for its surreal, often disquieting humor and bizarre plot twists. The series combines elements of comedy horror, science fantasy, and drama.\n\nThe program originated from a short on Cartoon Network's animation showcase series created by Hanna-Barbera president Fred Seibert, \"What a Cartoon!\" titled \"The Chicken from Outer Space\". The segment was nominated for an Academy Award in 1996, and Cartoon Network commissioned a series based on the short. The series, which premiered on November 12, 1999, ran for four seasons, ending on November 22, 2002 with a total of 52 episodes produced. The series was the sixth and final series to be spun off from World Premiere Toons, and it was the eighth series to fall under the Cartoon Cartoons label.",
      "first_air_date": "1999-11-12",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        10765,
        16,
        9648,
        35
      ],
      "original_language": "en",
      "vote_count": 19,
      "name": "Coraje, El Perro Cobarde",
      "original_name": "Coraje, El Perro Cobarde"
    },
    {
      "poster_path": "/1yeVJox3rjo2jBKrrihIMj7uoS9.jpg",
      "popularity": 21.173765,
      "id": 1396,
      "backdrop_path": "/eSzpy96DwBujGFj0xMbXBcGcfxX.jpg",
      "vote_average": 8.1,
      "overview": "Breaking Bad is an American crime drama television series created and produced by Vince Gilligan. Set and produced in Albuquerque, New Mexico, Breaking Bad is the story of Walter White, a struggling high school chemistry teacher who is diagnosed with inoperable lung cancer at the beginning of the series. He turns to a life of crime, producing and selling methamphetamine, in order to secure his family's financial future before he dies, teaming with his former student, Jesse Pinkman. Heavily serialized, the series is known for positioning its characters in seemingly inextricable corners and has been labeled a contemporary western by its creator.",
      "first_air_date": "2008-01-19",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 690,
      "name": "Breaking Bad",
      "original_name": "Breaking Bad"
    },
    {
      "poster_path": "/esKFbCWAGyUUNshT5HE5BIpvbcL.jpg",
      "popularity": 9.911993,
      "id": 66732,
      "backdrop_path": "/6c4weB3UycHwPgzv31Awt7nku9y.jpg",
      "vote_average": 8.08,
      "overview": "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces, and one strange little girl.",
      "first_air_date": "2016-07-15",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648,
        10765
      ],
      "original_language": "en",
      "vote_count": 77,
      "name": "Stranger Things",
      "original_name": "Stranger Things"
    },
    {
      "poster_path": "/mWNadwBZIx8NyEw4smGftYtHHrE.jpg",
      "popularity": 9.972256,
      "id": 1437,
      "backdrop_path": "/qlJB8bkK1JXAQ0m02OYS1ArS6DZ.jpg",
      "vote_average": 7.95,
      "overview": "Firefly is set in the year 2517, after the arrival of humans in a new star system and follows the adventures of the renegade crew of Serenity, a \"Firefly-class\" spaceship. The ensemble cast portrays the nine characters who live on Serenity.",
      "first_air_date": "2002-09-20",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        37,
        18,
        10759,
        10765
      ],
      "original_language": "en",
      "vote_count": 172,
      "name": "Firefly",
      "original_name": "Firefly"
    },
    {
      "poster_path": "/vHXZGe5tz4fcrqki9ZANkJISVKg.jpg",
      "popularity": 9.623731,
      "id": 19885,
      "backdrop_path": "/d6Aidd0YoC2WYEYSJRAl63kQnYK.jpg",
      "vote_average": 7.94,
      "overview": "A modern update finds the famous sleuth and his doctor partner solving crime in 21st century London.",
      "first_air_date": "2010-07-25",
      "origin_country": [
        "GB"
      ],
      "genre_ids": [
        80,
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 270,
      "name": "Sherlock",
      "original_name": "Sherlock"
    },
    {
      "poster_path": "/jIhL6mlT7AblhbHJgEoiBIOUVl1.jpg",
      "popularity": 29.780826,
      "id": 1399,
      "backdrop_path": "/mUkuc2wyV9dHLG0D0Loaw5pO2s8.jpg",
      "vote_average": 7.91,
      "overview": "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
      "first_air_date": "2011-04-17",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        10765,
        10759,
        18
      ],
      "original_language": "en",
      "vote_count": 1172,
      "name": "Game of Thrones",
      "original_name": "Game of Thrones"
    },
    {
      "poster_path": "/u0cLcBQITrYqfHsn06fxnQwtqiE.jpg",
      "popularity": 15.71135,
      "id": 1398,
      "backdrop_path": "/8GZ91vtbYOMp05qruAGPezWC0Ja.jpg",
      "vote_average": 7.87,
      "overview": "The Sopranos is an American television drama created by David Chase. The series revolves around the New Jersey-based Italian-American mobster Tony Soprano and the difficulties he faces as he tries to balance the conflicting requirements of his home life and the criminal organization he heads. Those difficulties are often highlighted through his ongoing professional relationship with psychiatrist Jennifer Melfi. The show features Tony's family members and Mafia associates in prominent roles and story arcs, most notably his wife Carmela and his cousin and protégé Christopher Moltisanti.",
      "first_air_date": "1999-01-10",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 121,
      "name": "The Sopranos",
      "original_name": "The Sopranos"
    },
    {
      "poster_path": "/4ukKkwZWDSCxdXKBWUEfLSuHWmS.jpg",
      "popularity": 3.7503,
      "id": 64439,
      "backdrop_path": "/28hMBZGoeKaz6LoNbztlDIoUQH9.jpg",
      "vote_average": 7.83,
      "overview": "Set in Americas Heartland, Making a Murderer follows the harrowing story of Steven Avery, an outsider from the wrong side of the tracks, convicted and later exonerated of a brutal assault. His release triggered major criminal justice reform legislation, and he filed a lawsuit that threatened to expose corruption in local law enforcement and award him millions of dollars. But in the midst of his very public civil case, he suddenly finds himself the prime suspect in a grisly new crime.",
      "first_air_date": "2015-12-18",
      "origin_country": [],
      "genre_ids": [
        99
      ],
      "original_language": "en",
      "vote_count": 30,
      "name": "Making a Murderer",
      "original_name": "Making a Murderer"
    },
    {
      "poster_path": "/ydmfheI5cJ4NrgcupDEwk8I8y5q.jpg",
      "popularity": 11.085982,
      "id": 1405,
      "backdrop_path": "/kgadTwNJYYGZ7LTrw9X7KDiRCfV.jpg",
      "vote_average": 7.79,
      "overview": "Dexter is an American television drama series. The series centers on Dexter Morgan, a blood spatter pattern analyst for 'Miami Metro Police Department' who also leads a secret life as a serial killer, hunting down criminals who have slipped through the cracks of justice.",
      "first_air_date": "2006-10-01",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 250,
      "name": "Dexter",
      "original_name": "Dexter"
    },
    {
      "poster_path": "/egrBNjbMKbUao19dJcSNiw4xlft.jpg",
      "popularity": 7.195255,
      "id": 46648,
      "backdrop_path": "/qDEIZWnyRxWTB9zCjyyj4mbURVp.jpg",
      "vote_average": 7.77,
      "overview": "An American anthology police detective series utilizing multiple timelines in which investigations seem to unearth personal and professional secrets of those involved, both within or outside the law.",
      "first_air_date": "2014-01-12",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "vote_count": 226,
      "name": "True Detective",
      "original_name": "True Detective"
    },
    {
      "poster_path": "/aYVBoq5MEtOBLlivSzDSpteZfXV.jpg",
      "popularity": 2.733919,
      "id": 31911,
      "backdrop_path": "/c368lahfH9sgdDHKp6ds7EprIga.jpg",
      "vote_average": 7.77,
      "overview": "Edward and Alphonse Elric's reckless disregard for alchemy's fun­damental laws ripped half of Ed's limbs from his body and left Al's soul clinging to a cold suit of armor. To restore what was lost, the brothers scour a war-torn land for the Philosopher's Sto­ne, a fabled relic which grants the ability to perform alchemy in impossible ways.\n\nThe Elrics are not alone in their search; the corrupt State Military is also eager to harness the artifact's power. So too are the strange Homunculi and their shadowy creator. The mythical gem lures exotic alchemists from distant kingdoms, scarring some deeply enough to inspire murder. As the Elrics find their course altered by these enemies and allies, their purpose remains unchanged – and their bond unbreakable.",
      "first_air_date": "2009-04-05",
      "origin_country": [
        "JP"
      ],
      "genre_ids": [
        16,
        18,
        10759,
        9648
      ],
      "original_language": "ja",
      "vote_count": 30,
      "name": "Fullmetal Alchemist: Brotherhood",
      "original_name": "鋼の錬金術師 FULLMETAL ALCHEMIST"
    },
    {
      "poster_path": "/wJKH0MHSvn3vS9fz9wF5IFpoquj.jpg",
      "popularity": 1.580899,
      "id": 1063,
      "backdrop_path": "/dYMycqFrk5AvRPczyAOwxAJv2TK.jpg",
      "vote_average": 7.76,
      "overview": "Mugen is a ferocious, animalistic warrior with a fighting style inspired by break-dancing. Jin is a ronin samurai who wanders the countryside alone. They may not be friends, but their paths continually cross. And when ditzy waitress Fuu gets them out of hot water with the local magistrate, they agree to join her search for the samurai who smells like sunflowers.",
      "first_air_date": "2004-05-20",
      "origin_country": [
        "JP"
      ],
      "genre_ids": [
        16,
        28,
        12
      ],
      "original_language": "ja",
      "vote_count": 17,
      "name": "Samurai Champloo",
      "original_name": "サムライチャンプルー"
    },
    {
      "poster_path": "/qen4mgSun5wy8fgSwXNR23surMM.jpg",
      "popularity": 1.395938,
      "id": 39218,
      "backdrop_path": "/rYIlgL5u4E7Jp1fyGKPOJYsSVWv.jpg",
      "vote_average": 7.75,
      "overview": "Madoka Kaname leads a happy life with her family and friends whilst attending Mitakihara School. One day, a magical creature called Kyuubey implores Madoka for help and from then on, she is drawn into a parallel world where magical girls battle against witches.",
      "first_air_date": "2011-01-07",
      "origin_country": [
        "JP"
      ],
      "genre_ids": [
        16,
        18,
        9648,
        10765
      ],
      "original_language": "ja",
      "vote_count": 10,
      "name": "Puella Magi Madoka Magica",
      "original_name": "魔法少女まどか☆マギカ"
    },
    {
      "poster_path": "/6wzfCXg2I2LBuaEjh7fkMHXBR9i.jpg",
      "popularity": 3.373494,
      "id": 1920,
      "backdrop_path": "/3Y91NnZZyTS8UbgJUw3AZ6WWKTN.jpg",
      "vote_average": 7.75,
      "overview": "The body of Laura Palmer is washed up on a beach near the small Washington state town of Twin Peaks. FBI Special Agent Dale Cooper is called in to investigate her strange demise only to uncover a web of mystery that ultimately leads him deep into the heart of the surrounding woodland and his very own soul.",
      "first_air_date": "1990-04-08",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        9648,
        53
      ],
      "original_language": "en",
      "vote_count": 62,
      "name": "Twin Peaks",
      "original_name": "Twin Peaks"
    },
    {
      "poster_path": "/lxSzRZ49NXwsiyHuvMsd19QxduC.jpg",
      "popularity": 12.394738,
      "id": 1408,
      "backdrop_path": "/6r5o8yvLx7nESRBC1iMuYBCk9Cj.jpg",
      "vote_average": 7.75,
      "overview": "Dr. Gregory House, a drug-addicted, unconventional, misanthropic medical genius, leads a team of diagnosticians at the fictional Princeton–Plainsboro Teaching Hospital in New Jersey.",
      "first_air_date": "2004-11-16",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        18,
        35,
        9648
      ],
      "original_language": "en",
      "vote_count": 171,
      "name": "House",
      "original_name": "House"
    },
    {
      "poster_path": "/dg7NuKDjmS6OzuNy33qt8kSkPA1.jpg",
      "popularity": 4.51393,
      "id": 1438,
      "backdrop_path": "/4hWfYN3wiOZZXC7t6B70BF9iUvk.jpg",
      "vote_average": 7.75,
      "overview": "The Wire is an American television crime drama series set and produced in and around Baltimore, Maryland. Each season of The Wire introduces a different facet of the city of Baltimore. In chronological order they are: the illegal drug trade, the seaport system, the city government and bureaucracy, the school system, and the print news media.\n\nDespite only receiving average ratings and never winning major television awards, The Wire has been described by many critics and fans as one of the greatest TV dramas of all time. The show is recognized for its realistic portrayal of urban life, its literary ambitions, and its uncommonly deep exploration of sociopolitical themes.",
      "first_air_date": "2002-06-02",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        80,
        18,
        9648
      ],
      "original_language": "en",
      "vote_count": 100,
      "name": "The Wire",
      "original_name": "The Wire"
    },
    {
      "poster_path": "/iiYFBpjAbQUzsu0k4LDvWqBiQzI.jpg",
      "popularity": 2.855247,
      "id": 2490,
      "backdrop_path": "/fZoj7ZMLbBQjC8MvQjZ3XuzqLwp.jpg",
      "vote_average": 7.73,
      "overview": "UK Comedy series about two I.T. nerds and their clueless female manager, who work in the basement of a very successful company. When they are called on for help, they are never treated with any respect at all.",
      "first_air_date": "2006-02-03",
      "origin_country": [
        "GB"
      ],
      "genre_ids": [
        35,
        18
      ],
      "original_language": "en",
      "vote_count": 81,
      "name": "The IT Crowd",
      "original_name": "The IT Crowd"
    },
    {
      "poster_path": "/boh1E1atURBdHXjboTnWOKIfWKb.jpg",
      "popularity": 1.369815,
      "id": 3579,
      "backdrop_path": "/2GWeOe5dhM3BtK94FZ2vjXACvam.jpg",
      "vote_average": 7.73,
      "overview": "The Angry Beavers is an American animated television series created by Mitch Schauer for the Nickelodeon channel. The series revolves around Daggett and Norbert Beaver, two young beaver brothers who have left their home to become bachelors in the forest near the fictional Wayouttatown, Oregon. The show premiered in the United States on April 19, 1997. The show started airing on the Nickelodeon Canada channel when it launched on November 2, 2009. The series aired on The '90s Are All That block on TeenNick in the US on October 7, 2011 as part of the block's U Pick with Stick line-up. The series was also up for a U Pick with Stick showdown on The '90s Are All That for the weekend of February 3, 2012, but lost to Rocko's Modern Life and did not air. The series was added to the Sunday line-up on The '90s Are All That on TeenNick, and aired from February 10, 2013, to March 3, 2013. The series returned to The '90s Are All That on TeenNick on March 25, 2013, but has since left the line-up again. The series is also currently being released on DVD.",
      "first_air_date": "1997-04-20",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        16,
        35,
        10751
      ],
      "original_language": "en",
      "vote_count": 11,
      "name": "The Angry Beavers",
      "original_name": "The Angry Beavers"
    },
    {
      "poster_path": "/sskPK2HjkFaxam10eg0Hk1A3I2m.jpg",
      "popularity": 6.055152,
      "id": 60622,
      "backdrop_path": "/qq1S5EjaaUrQAcMsn6raNFXpzHk.jpg",
      "vote_average": 7.72,
      "overview": "A close-knit anthology series dealing with stories involving malice, violence and murder based in and around Minnesota.",
      "first_air_date": "2014-04-15",
      "origin_country": [
        "US"
      ],
      "genre_ids": [
        80,
        18,
        53
      ],
      "original_language": "en",
      "vote_count": 118,
      "name": "Fargo",
      "original_name": "Fargo"
    }
  ],
  "total_results": 747,
  "total_pages": 38
}
"""
}