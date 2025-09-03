package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PollResourceTest {
    @Test
    public void testCreatePoll() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.title = "test poll";
        createPollRequest.description = "test poll description";

        //Act & Assert
        PollResponse pollResponse =
            given()
                    .contentType(ContentType.JSON)
                    .body(createPollRequest)
                    .when()
                    .post("/polls")
                    .then()
                    .statusCode(201)
                    .extract().as(PollResponse.class);

        assertEquals("test poll", pollResponse.title);
        assertEquals("test poll description", pollResponse.description);
        assertNotNull(pollResponse.id);
        assertNotNull(pollResponse.createdTimestamp);
    }

    @Test
    public void testCreatePoll_TitleNull() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.description = "test poll description";

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(createPollRequest)
                .when()
                .post("/polls")
                .then()
                .statusCode(400);
    }


    @Test
    public void testCreatePoll_TitleMaxLength() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.title = "ofcnsqnfkkprtokxzfimatehnvaylpykizxxnzorihjmzwmfwgemgogcueoizhdqlthjgwbzxbwjmwmhgdzdznwwsywwnpktokfejkjkmqvnrjczljliuowfpkzpguzcnebyldyhfetvnhbmyooiivcihyhzfdqxodemxqnorbqukykgigedgmbykfbozzztdhyoqsao";
        createPollRequest.description = "test poll description";

        //Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(createPollRequest)
                        .when()
                        .post("/polls")
                        .then()
                        .statusCode(201)
                        .extract().as(PollResponse.class);

        assertEquals("ofcnsqnfkkprtokxzfimatehnvaylpykizxxnzorihjmzwmfwgemgogcueoizhdqlthjgwbzxbwjmwmhgdzdznwwsywwnpktokfejkjkmqvnrjczljliuowfpkzpguzcnebyldyhfetvnhbmyooiivcihyhzfdqxodemxqnorbqukykgigedgmbykfbozzztdhyoqsao"
                , pollResponse.title);
        assertEquals("test poll description", pollResponse.description);
        assertNotNull(pollResponse.id);
        assertNotNull(pollResponse.createdTimestamp);
    }


    @Test
    public void testCreatePoll_TitleTooLong() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.title = "test poll";
        createPollRequest.description = "ofcnsqnfkkprtokxzfimatehnvaylpykizxxnzorihjmzwmfwgemgogcueoizhdqlthjgwbzxbwjmwmhgdzdznwwsywwnpktokfejkjkmqvnrjczljliuowfpkzpguzcnebyldyhfetvnhbmyooiivcihyhzfdqxodemxqnorbqukykgigedgmbykfbozzztdhyoqsaoq";

        //Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(createPollRequest)
                        .when()
                        .post("/polls")
                        .then()
                        .statusCode(201)
                        .extract().as(PollResponse.class);

        assertEquals("test poll", pollResponse.title);
        assertEquals("ofcnsqnfkkprtokxzfimatehnvaylpykizxxnzorihjmzwmfwgemgogcueoizhdqlthjgwbzxbwjmwmhgdzdznwwsywwnpktokfejkjkmqvnrjczljliuowfpkzpguzcnebyldyhfetvnhbmyooiivcihyhzfdqxodemxqnorbqukykgigedgmbykfbozzztdhyoqsaoq", pollResponse.description);
        assertNotNull(pollResponse.id);
        assertNotNull(pollResponse.createdTimestamp);
    }


    @Test
    public void testCreatePoll_TitleEmpty() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.title = "";
        createPollRequest.description = "test poll description";

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(createPollRequest)
                .when()
                .post("/polls")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreatePoll_DescriptionMaxLength() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.title = "test poll";
        createPollRequest.description = "gffoldvcosxewlbaprzukliheymvfkhafjaonlgnyuajkztnnxbknpzqdktyjydnzabsiihljzrqnsxacgfmmeoveujlxhycxomzjefvnjgphmdslrwuppgeiyxmscotrbrokvxcnibtnkbtepqjhojolmfvvagmmkvypbjckeubslultzsinomuirpoldjjyijomxbdikvbiaycodfluzhaggfuegfsnotmjntlonnawjktjushtmihcdpavfcijnudzdckxkfeumxducosrvfypnqvdwlijbffolamnqlhhqqmmgygazrtjwwctebbgznuotnzbtxzhyffcyotclviehfyimlfzigfdldnqzrrscelyduzcstnhviawmaaspekkjngyileizqtajbrailtsneyjoduzxvxwmgmphedpeopoaakqtomwfstviholyczkexshxzmemjuixxitmazxtdsreinkyxafiochjnqciofspxvmgoqwcahjszidpzcovswhrxrvjkhfrovgkytvkkyshdsdkxyinywlihvrfvdvnpcavbtqhbdataooirfvrhdxflxhjxzodjmvqhiufvdbuodeaimkprkvtgavpszvatarvvfertrtgcmktdbxacmautiudyekeotkojifbdibzvjanxlyaviacuxghginoryzhogybjebykqigfipboxmhjeyoxkenugbtspudhyxczfkqbjoqhbghhcuhklunpbkrbdnrdqqmolxtapwnyicywtmydlfubuepcgedxeqosnjiqnkhrbskxywatjvmxbrpfkuojbxcssvybthvadxcqdtsduqyifnnsnyirqxcxmvbluqvolvnqbfvfbnvrnuiyugkvqgpfqmikbgpmbjofmuhbvrzfdbmbxfrztfxiambjnuqcalneytkdgvtqmawwlpbsztnfyiiertitvzabgbnbqvdfxvmcuvpmadeilqjkqvjpmlribfycjsmlzrvtbytbidwoxhtvafsnznakorfawudgscjrxhofyufpfagncshzjxwjafkislqmkavyzfxukqeuefohbnofhweacfoiuykewhaoqbxkimgsqkodyxkaztiooadsezgzzwezilvwcrybufvxdelhlzkfxfnragegyhuxjuxamwexhnagbwvugqlmxfsbrflvgajssmdqygljovdhpnccdphfdsxgpyekxbpdfvmnvnobawdwlzscyaqvrlserurqcyjmckpqgcnnqzbynsptuocfwuswhnhrrtffbbspbzlznafsylgxvhzaujeqehwxbihaepwjdewwqurunazzfwsgzinpngcwycjelezxupysbqpdwdvtlmpdpbktbvujnpbjmrcyoswlvvyovpwdpfpfcccukhcckjwolfqtrcxsvuqjgbidskeghzevemazrskdtwgwnrgijxbnlffmsyscsjoqovxqapkgeqsmndxpmrcgbcsgcdfxfubmprphsaxibdcjtztqlpmgpmbzwgrxecwaypyeizuguwiusqessrmtcaotczirtkmbaeoosolsfptythnyncafxpeqlwxuqaajrfdariaursirvpqngtwsgkcoolamuyotywsjlshebsxyginmhogznkckyruvirnexfwgkfsnpghdfcwljqyfijziamkvkjjzhjflfpnsfxwjgbwkmompphnuvllqhodztoajrqqwhvhvfwmqatzzgulncvkjwfxgdsiqrkettclytnltmnnwzehnnwkdhqmflclzxbkhlslbbdftywyhvvanhjkjczorqiposmtraaibxoqyqtaaqvqkkcsqmmndqwuydyvvnwwqqtrroyauksqxvrsjyjcnbkcbupszhygvjbwbayzfbnsujcwxlxztcnhrdgldbgpocnbbsnryzdnzxgmgebfuseiyvstoughyliqulrvlprbztjrzusfhevkarfziholgklkczfpshvvcpydurynjkamistequfxayzrfukrgpbfwqazvammfkkwncddgojexglgdgdocgsopdjdoqievnaocrjurbqyuafjnrjyvrntocxoxaabpqbnujxluyifwiiqsrxiibjpagustjxripyurpjifakkjyvqikzvecoxbtwgcfxgtmzlywmhxizxvkrcntqkzeukpvouzhjnbgbecsrzywiyqisvvpqojteibqfnztzvouaczsvvmtugvcesorcnfytjbgkguldoxewbtxiympgynlynphftezgawdbjomftcizurbpsxujkpxvfgqcqluegdmnngzktkogwrcshafrqnpisocwptlyxqwqbfzexoqjjhndidtszvpraefenbqpccaawtcijtjurgorcmkdrrqibjtweevngcookfbikcdiaobmlzkdorefqltqnlemavnnewlhxpyjoloteyowqxaskxhkksgfqsolanrdgifumwwgabjnxrxdurqegbwtvrqbafqmxfeydskckpefvxzjufckvqfkqwsppmhepfqdxvmhqjhobpiejbjcvxxdwxqjiarltymdpltypjotcrewyjefyresqmfvmtrynhwvsjmiejifbtfmmceoarzwletqhfbytavnpojgnajjjvssfvyrxhzzfyljcrkxufjcphjyuktntudkpebkhvciswpjhumfzcvtbhnucfnocwuszgwcedqhqbruymkorzxocqckvgbkopprwyabervvikghincvoxcbhchgywbrmutjlhimqsxjhhjlfllrvzdsgoqxwgydmzinfhvvfqeyrfdupabhnezbzyuduviewvzlyrkshqvqyuqdofpylebxhbrvdjhqzrkqgudgfecahglrvijrmhjfaoblwugebajvaijajmuzwbrpiqzumpczpfoxogwlbnrbxbxhtgzjsarheubrwprlkaspnrctotiykjywwutqbzumrovgxrqpzmwktnaldmwlfovkpxgxmsowmgdoqxrcplhvpexpntngowibyrkjmubepsjbfwcqjpkamcttgstrnjawnsizrdmlrdqrmuxohlcauawnyisqqzzayzlmigmhyjrgxsiafxfsvlxnbfczjtzicyfxnzakqbefwrpprjgufwqkfprnezjjnyqgztagcpbflsvxiijwnmfvvfkefpqqpewjghmwwiuyboskwnxetfnszjkbeifrhwpaumkjequyvhairyaiitxjxaflvsffkdwaclogkufwalypkpywkusmksxfsvdlvdyqtcpoaekrwyuwbcxnxydkiwvslwjpbwcuuzoiquungcvvjagnaslcwdujsfxdbeiwofadupqwzznoeprjwuhmsjkdctjxgpnebxzptoqqnzbjrwylagxyhjzpffbwzgwhdscjsclbbqqgnevkeqofxiqwplcepjchkeaqerzhxsyvcmtddywdljhhokhpgqsncuvdqobviklckipyavtsbtuszrquhkmmbdzlgjyhpemzughainodjfvxchyvgtkdyaqeajokkzgwhwgmdkggoiuskxvxjwhsfsdaavgwkuhrizvdtylptrbsxqpbjjjwerjxepcaciymtxtcumvqdbhvefpvlzxtrioqxzebzfrghgskheomvfgduosoyrcjzjbrhquesqyblbnbnjcvfowpfiwwhscjqnjzoytivcgulhpmxzvaldqhitbxbpcxcqbytusbubqucswsiokxankojqyhygtghdspuquslqqmjknzsumnynxxtsqtnabfcmcfeiqsjatlavquxqstcbdxznewrgmkmvnylkuubizqrksbmssbctvxuyiupbsgjflzlxeotmomuxlensomxunmwcdgvxzivurgtcmtunwqxyjzttnjpdqmbhdfgsnylingvymtylxnxldmlfsafrwakeubpdcmvfkidykrugrpjuuecmuiyovszjfuiiobstnnzgufmsgmmhxzvtoxychwhkbnjkmwkdxpkvhenczvssnowgzrtzuxwjulotvssbjkvfskgsntsvprfwakwpruruopbvgrkweofgscdilsaietzaaagjmrqskrxtjvytwbyrsphxbhdramcwwftrskvpfehjycwcgmkbbgyuvzgxyfpqlitnylkurhxvybcbyftwpwnrwgapobplivjwssoaegcldgftxjxhboejxyeveuajoxapiztkrvckajxdewpzhmvfgeuzhskjdgiwmlriymcmcixklqtuotcirmztkbqpgaretxtmoqzaesevahxeknfeqfhibojklrpgtrwomsadcpurtladktxvdmvorxqfftftjhubkkeipxqsgjnyvkacibcnnnppveeqnhzenhcchcciybakzdpuzqexnqqnrmljhpzrlfimjxyuragckfddrkgoymqdlpzwmwuonwpesubyurlxxtxfvhkqqavvfevgxbrxghxqftqztjkoqwopiftkvmjeabdffeitpydekihpfmparasqshumabwxfinytrqnconindlrntjjsfurdqeguindsrofaclpsisdexotnvxiekbyqotxilpeukeoprcydrvcecisjtmzqgwbideneydnbuoihptjhlikyjzvaqnxhjyealclioppptkvauibfzywonegamaeyzvoactgmcxtwrtbebwpszuyhbwvjmpnefbckeoshtrjqdghpengycesjofdmcbaoxjbqlkjbwqeutmlfzglvgkxzcuedwsrdkdyjsjjyfrkucyzqbfrouumoojgzwpaskqtvrvaaplhatnwievxmpoaljzuxvadggvuqpxpgghzbjajaxfuaosxrriussgovbrqdmvkvaqzfkvfwtypeeitsmwqzhwgzdnzuvwvtskciwunqomitpujpyvnj";

        //Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(createPollRequest)
                        .when()
                        .post("/polls")
                        .then()
                        .statusCode(201)
                        .extract().as(PollResponse.class);

        assertEquals("test poll", pollResponse.title);
        assertEquals("gffoldvcosxewlbaprzukliheymvfkhafjaonlgnyuajkztnnxbknpzqdktyjydnzabsiihljzrqnsxacgfmmeoveujlxhycxomzjefvnjgphmdslrwuppgeiyxmscotrbrokvxcnibtnkbtepqjhojolmfvvagmmkvypbjckeubslultzsinomuirpoldjjyijomxbdikvbiaycodfluzhaggfuegfsnotmjntlonnawjktjushtmihcdpavfcijnudzdckxkfeumxducosrvfypnqvdwlijbffolamnqlhhqqmmgygazrtjwwctebbgznuotnzbtxzhyffcyotclviehfyimlfzigfdldnqzrrscelyduzcstnhviawmaaspekkjngyileizqtajbrailtsneyjoduzxvxwmgmphedpeopoaakqtomwfstviholyczkexshxzmemjuixxitmazxtdsreinkyxafiochjnqciofspxvmgoqwcahjszidpzcovswhrxrvjkhfrovgkytvkkyshdsdkxyinywlihvrfvdvnpcavbtqhbdataooirfvrhdxflxhjxzodjmvqhiufvdbuodeaimkprkvtgavpszvatarvvfertrtgcmktdbxacmautiudyekeotkojifbdibzvjanxlyaviacuxghginoryzhogybjebykqigfipboxmhjeyoxkenugbtspudhyxczfkqbjoqhbghhcuhklunpbkrbdnrdqqmolxtapwnyicywtmydlfubuepcgedxeqosnjiqnkhrbskxywatjvmxbrpfkuojbxcssvybthvadxcqdtsduqyifnnsnyirqxcxmvbluqvolvnqbfvfbnvrnuiyugkvqgpfqmikbgpmbjofmuhbvrzfdbmbxfrztfxiambjnuqcalneytkdgvtqmawwlpbsztnfyiiertitvzabgbnbqvdfxvmcuvpmadeilqjkqvjpmlribfycjsmlzrvtbytbidwoxhtvafsnznakorfawudgscjrxhofyufpfagncshzjxwjafkislqmkavyzfxukqeuefohbnofhweacfoiuykewhaoqbxkimgsqkodyxkaztiooadsezgzzwezilvwcrybufvxdelhlzkfxfnragegyhuxjuxamwexhnagbwvugqlmxfsbrflvgajssmdqygljovdhpnccdphfdsxgpyekxbpdfvmnvnobawdwlzscyaqvrlserurqcyjmckpqgcnnqzbynsptuocfwuswhnhrrtffbbspbzlznafsylgxvhzaujeqehwxbihaepwjdewwqurunazzfwsgzinpngcwycjelezxupysbqpdwdvtlmpdpbktbvujnpbjmrcyoswlvvyovpwdpfpfcccukhcckjwolfqtrcxsvuqjgbidskeghzevemazrskdtwgwnrgijxbnlffmsyscsjoqovxqapkgeqsmndxpmrcgbcsgcdfxfubmprphsaxibdcjtztqlpmgpmbzwgrxecwaypyeizuguwiusqessrmtcaotczirtkmbaeoosolsfptythnyncafxpeqlwxuqaajrfdariaursirvpqngtwsgkcoolamuyotywsjlshebsxyginmhogznkckyruvirnexfwgkfsnpghdfcwljqyfijziamkvkjjzhjflfpnsfxwjgbwkmompphnuvllqhodztoajrqqwhvhvfwmqatzzgulncvkjwfxgdsiqrkettclytnltmnnwzehnnwkdhqmflclzxbkhlslbbdftywyhvvanhjkjczorqiposmtraaibxoqyqtaaqvqkkcsqmmndqwuydyvvnwwqqtrroyauksqxvrsjyjcnbkcbupszhygvjbwbayzfbnsujcwxlxztcnhrdgldbgpocnbbsnryzdnzxgmgebfuseiyvstoughyliqulrvlprbztjrzusfhevkarfziholgklkczfpshvvcpydurynjkamistequfxayzrfukrgpbfwqazvammfkkwncddgojexglgdgdocgsopdjdoqievnaocrjurbqyuafjnrjyvrntocxoxaabpqbnujxluyifwiiqsrxiibjpagustjxripyurpjifakkjyvqikzvecoxbtwgcfxgtmzlywmhxizxvkrcntqkzeukpvouzhjnbgbecsrzywiyqisvvpqojteibqfnztzvouaczsvvmtugvcesorcnfytjbgkguldoxewbtxiympgynlynphftezgawdbjomftcizurbpsxujkpxvfgqcqluegdmnngzktkogwrcshafrqnpisocwptlyxqwqbfzexoqjjhndidtszvpraefenbqpccaawtcijtjurgorcmkdrrqibjtweevngcookfbikcdiaobmlzkdorefqltqnlemavnnewlhxpyjoloteyowqxaskxhkksgfqsolanrdgifumwwgabjnxrxdurqegbwtvrqbafqmxfeydskckpefvxzjufckvqfkqwsppmhepfqdxvmhqjhobpiejbjcvxxdwxqjiarltymdpltypjotcrewyjefyresqmfvmtrynhwvsjmiejifbtfmmceoarzwletqhfbytavnpojgnajjjvssfvyrxhzzfyljcrkxufjcphjyuktntudkpebkhvciswpjhumfzcvtbhnucfnocwuszgwcedqhqbruymkorzxocqckvgbkopprwyabervvikghincvoxcbhchgywbrmutjlhimqsxjhhjlfllrvzdsgoqxwgydmzinfhvvfqeyrfdupabhnezbzyuduviewvzlyrkshqvqyuqdofpylebxhbrvdjhqzrkqgudgfecahglrvijrmhjfaoblwugebajvaijajmuzwbrpiqzumpczpfoxogwlbnrbxbxhtgzjsarheubrwprlkaspnrctotiykjywwutqbzumrovgxrqpzmwktnaldmwlfovkpxgxmsowmgdoqxrcplhvpexpntngowibyrkjmubepsjbfwcqjpkamcttgstrnjawnsizrdmlrdqrmuxohlcauawnyisqqzzayzlmigmhyjrgxsiafxfsvlxnbfczjtzicyfxnzakqbefwrpprjgufwqkfprnezjjnyqgztagcpbflsvxiijwnmfvvfkefpqqpewjghmwwiuyboskwnxetfnszjkbeifrhwpaumkjequyvhairyaiitxjxaflvsffkdwaclogkufwalypkpywkusmksxfsvdlvdyqtcpoaekrwyuwbcxnxydkiwvslwjpbwcuuzoiquungcvvjagnaslcwdujsfxdbeiwofadupqwzznoeprjwuhmsjkdctjxgpnebxzptoqqnzbjrwylagxyhjzpffbwzgwhdscjsclbbqqgnevkeqofxiqwplcepjchkeaqerzhxsyvcmtddywdljhhokhpgqsncuvdqobviklckipyavtsbtuszrquhkmmbdzlgjyhpemzughainodjfvxchyvgtkdyaqeajokkzgwhwgmdkggoiuskxvxjwhsfsdaavgwkuhrizvdtylptrbsxqpbjjjwerjxepcaciymtxtcumvqdbhvefpvlzxtrioqxzebzfrghgskheomvfgduosoyrcjzjbrhquesqyblbnbnjcvfowpfiwwhscjqnjzoytivcgulhpmxzvaldqhitbxbpcxcqbytusbubqucswsiokxankojqyhygtghdspuquslqqmjknzsumnynxxtsqtnabfcmcfeiqsjatlavquxqstcbdxznewrgmkmvnylkuubizqrksbmssbctvxuyiupbsgjflzlxeotmomuxlensomxunmwcdgvxzivurgtcmtunwqxyjzttnjpdqmbhdfgsnylingvymtylxnxldmlfsafrwakeubpdcmvfkidykrugrpjuuecmuiyovszjfuiiobstnnzgufmsgmmhxzvtoxychwhkbnjkmwkdxpkvhenczvssnowgzrtzuxwjulotvssbjkvfskgsntsvprfwakwpruruopbvgrkweofgscdilsaietzaaagjmrqskrxtjvytwbyrsphxbhdramcwwftrskvpfehjycwcgmkbbgyuvzgxyfpqlitnylkurhxvybcbyftwpwnrwgapobplivjwssoaegcldgftxjxhboejxyeveuajoxapiztkrvckajxdewpzhmvfgeuzhskjdgiwmlriymcmcixklqtuotcirmztkbqpgaretxtmoqzaesevahxeknfeqfhibojklrpgtrwomsadcpurtladktxvdmvorxqfftftjhubkkeipxqsgjnyvkacibcnnnppveeqnhzenhcchcciybakzdpuzqexnqqnrmljhpzrlfimjxyuragckfddrkgoymqdlpzwmwuonwpesubyurlxxtxfvhkqqavvfevgxbrxghxqftqztjkoqwopiftkvmjeabdffeitpydekihpfmparasqshumabwxfinytrqnconindlrntjjsfurdqeguindsrofaclpsisdexotnvxiekbyqotxilpeukeoprcydrvcecisjtmzqgwbideneydnbuoihptjhlikyjzvaqnxhjyealclioppptkvauibfzywonegamaeyzvoactgmcxtwrtbebwpszuyhbwvjmpnefbckeoshtrjqdghpengycesjofdmcbaoxjbqlkjbwqeutmlfzglvgkxzcuedwsrdkdyjsjjyfrkucyzqbfrouumoojgzwpaskqtvrvaaplhatnwievxmpoaljzuxvadggvuqpxpgghzbjajaxfuaosxrriussgovbrqdmvkvaqzfkvfwtypeeitsmwqzhwgzdnzuvwvtskciwunqomitpujpyvnj"
                , pollResponse.description);
        assertNotNull(pollResponse.id);
        assertNotNull(pollResponse.createdTimestamp);
    }

    @Test
    public void testCreatePoll_DescriptionTooLong() {
        //Arrange
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.title = "test poll title";
        createPollRequest.description = "gffoldvcosxewlbaprzukliheymvfkhafjaonlgnyuajkztnnxbknpzqdktyjydnzabsiihljzrqnsxacgfmmeoveujlxhycxomzjefvnjgphmdslrwuppgeiyxmscotrbrokvxcnibtnkbtepqjhojolmfvvagmmkvypbjckeubslultzsinomuirpoldjjyijomxbdikvbiaycodfluzhaggfuegfsnotmjntlonnawjktjushtmihcdpavfcijnudzdckxkfeumxducosrvfypnqvdwlijbffolamnqlhhqqmmgygazrtjwwctebbgznuotnzbtxzhyffcyotclviehfyimlfzigfdldnqzrrscelyduzcstnhviawmaaspekkjngyileizqtajbrailtsneyjoduzxvxwmgmphedpeopoaakqtomwfstviholyczkexshxzmemjuixxitmazxtdsreinkyxafiochjnqciofspxvmgoqwcahjszidpzcovswhrxrvjkhfrovgkytvkkyshdsdkxyinywlihvrfvdvnpcavbtqhbdataooirfvrhdxflxhjxzodjmvqhiufvdbuodeaimkprkvtgavpszvatarvvfertrtgcmktdbxacmautiudyekeotkojifbdibzvjanxlyaviacuxghginoryzhogybjebykqigfipboxmhjeyoxkenugbtspudhyxczfkqbjoqhbghhcuhklunpbkrbdnrdqqmolxtapwnyicywtmydlfubuepcgedxeqosnjiqnkhrbskxywatjvmxbrpfkuojbxcssvybthvadxcqdtsduqyifnnsnyirqxcxmvbluqvolvnqbfvfbnvrnuiyugkvqgpfqmikbgpmbjofmuhbvrzfdbmbxfrztfxiambjnuqcalneytkdgvtqmawwlpbsztnfyiiertitvzabgbnbqvdfxvmcuvpmadeilqjkqvjpmlribfycjsmlzrvtbytbidwoxhtvafsnznakorfawudgscjrxhofyufpfagncshzjxwjafkislqmkavyzfxukqeuefohbnofhweacfoiuykewhaoqbxkimgsqkodyxkaztiooadsezgzzwezilvwcrybufvxdelhlzkfxfnragegyhuxjuxamwexhnagbwvugqlmxfsbrflvgajssmdqygljovdhpnccdphfdsxgpyekxbpdfvmnvnobawdwlzscyaqvrlserurqcyjmckpqgcnnqzbynsptuocfwuswhnhrrtffbbspbzlznafsylgxvhzaujeqehwxbihaepwjdewwqurunazzfwsgzinpngcwycjelezxupysbqpdwdvtlmpdpbktbvujnpbjmrcyoswlvvyovpwdpfpfcccukhcckjwolfqtrcxsvuqjgbidskeghzevemazrskdtwgwnrgijxbnlffmsyscsjoqovxqapkgeqsmndxpmrcgbcsgcdfxfubmprphsaxibdcjtztqlpmgpmbzwgrxecwaypyeizuguwiusqessrmtcaotczirtkmbaeoosolsfptythnyncafxpeqlwxuqaajrfdariaursirvpqngtwsgkcoolamuyotywsjlshebsxyginmhogznkckyruvirnexfwgkfsnpghdfcwljqyfijziamkvkjjzhjflfpnsfxwjgbwkmompphnuvllqhodztoajrqqwhvhvfwmqatzzgulncvkjwfxgdsiqrkettclytnltmnnwzehnnwkdhqmflclzxbkhlslbbdftywyhvvanhjkjczorqiposmtraaibxoqyqtaaqvqkkcsqmmndqwuydyvvnwwqqtrroyauksqxvrsjyjcnbkcbupszhygvjbwbayzfbnsujcwxlxztcnhrdgldbgpocnbbsnryzdnzxgmgebfuseiyvstoughyliqulrvlprbztjrzusfhevkarfziholgklkczfpshvvcpydurynjkamistequfxayzrfukrgpbfwqazvammfkkwncddgojexglgdgdocgsopdjdoqievnaocrjurbqyuafjnrjyvrntocxoxaabpqbnujxluyifwiiqsrxiibjpagustjxripyurpjifakkjyvqikzvecoxbtwgcfxgtmzlywmhxizxvkrcntqkzeukpvouzhjnbgbecsrzywiyqisvvpqojteibqfnztzvouaczsvvmtugvcesorcnfytjbgkguldoxewbtxiympgynlynphftezgawdbjomftcizurbpsxujkpxvfgqcqluegdmnngzktkogwrcshafrqnpisocwptlyxqwqbfzexoqjjhndidtszvpraefenbqpccaawtcijtjurgorcmkdrrqibjtweevngcookfbikcdiaobmlzkdorefqltqnlemavnnewlhxpyjoloteyowqxaskxhkksgfqsolanrdgifumwwgabjnxrxdurqegbwtvrqbafqmxfeydskckpefvxzjufckvqfkqwsppmhepfqdxvmhqjhobpiejbjcvxxdwxqjiarltymdpltypjotcrewyjefyresqmfvmtrynhwvsjmiejifbtfmmceoarzwletqhfbytavnpojgnajjjvssfvyrxhzzfyljcrkxufjcphjyuktntudkpebkhvciswpjhumfzcvtbhnucfnocwuszgwcedqhqbruymkorzxocqckvgbkopprwyabervvikghincvoxcbhchgywbrmutjlhimqsxjhhjlfllrvzdsgoqxwgydmzinfhvvfqeyrfdupabhnezbzyuduviewvzlyrkshqvqyuqdofpylebxhbrvdjhqzrkqgudgfecahglrvijrmhjfaoblwugebajvaijajmuzwbrpiqzumpczpfoxogwlbnrbxbxhtgzjsarheubrwprlkaspnrctotiykjywwutqbzumrovgxrqpzmwktnaldmwlfovkpxgxmsowmgdoqxrcplhvpexpntngowibyrkjmubepsjbfwcqjpkamcttgstrnjawnsizrdmlrdqrmuxohlcauawnyisqqzzayzlmigmhyjrgxsiafxfsvlxnbfczjtzicyfxnzakqbefwrpprjgufwqkfprnezjjnyqgztagcpbflsvxiijwnmfvvfkefpqqpewjghmwwiuyboskwnxetfnszjkbeifrhwpaumkjequyvhairyaiitxjxaflvsffkdwaclogkufwalypkpywkusmksxfsvdlvdyqtcpoaekrwyuwbcxnxydkiwvslwjpbwcuuzoiquungcvvjagnaslcwdujsfxdbeiwofadupqwzznoeprjwuhmsjkdctjxgpnebxzptoqqnzbjrwylagxyhjzpffbwzgwhdscjsclbbqqgnevkeqofxiqwplcepjchkeaqerzhxsyvcmtddywdljhhokhpgqsncuvdqobviklckipyavtsbtuszrquhkmmbdzlgjyhpemzughainodjfvxchyvgtkdyaqeajokkzgwhwgmdkggoiuskxvxjwhsfsdaavgwkuhrizvdtylptrbsxqpbjjjwerjxepcaciymtxtcumvqdbhvefpvlzxtrioqxzebzfrghgskheomvfgduosoyrcjzjbrhquesqyblbnbnjcvfowpfiwwhscjqnjzoytivcgulhpmxzvaldqhitbxbpcxcqbytusbubqucswsiokxankojqyhygtghdspuquslqqmjknzsumnynxxtsqtnabfcmcfeiqsjatlavquxqstcbdxznewrgmkmvnylkuubizqrksbmssbctvxuyiupbsgjflzlxeotmomuxlensomxunmwcdgvxzivurgtcmtunwqxyjzttnjpdqmbhdfgsnylingvymtylxnxldmlfsafrwakeubpdcmvfkidykrugrpjuuecmuiyovszjfuiiobstnnzgufmsgmmhxzvtoxychwhkbnjkmwkdxpkvhenczvssnowgzrtzuxwjulotvssbjkvfskgsntsvprfwakwpruruopbvgrkweofgscdilsaietzaaagjmrqskrxtjvytwbyrsphxbhdramcwwftrskvpfehjycwcgmkbbgyuvzgxyfpqlitnylkurhxvybcbyftwpwnrwgapobplivjwssoaegcldgftxjxhboejxyeveuajoxapiztkrvckajxdewpzhmvfgeuzhskjdgiwmlriymcmcixklqtuotcirmztkbqpgaretxtmoqzaesevahxeknfeqfhibojklrpgtrwomsadcpurtladktxvdmvorxqfftftjhubkkeipxqsgjnyvkacibcnnnppveeqnhzenhcchcciybakzdpuzqexnqqnrmljhpzrlfimjxyuragckfddrkgoymqdlpzwmwuonwpesubyurlxxtxfvhkqqavvfevgxbrxghxqftqztjkoqwopiftkvmjeabdffeitpydekihpfmparasqshumabwxfinytrqnconindlrntjjsfurdqeguindsrofaclpsisdexotnvxiekbyqotxilpeukeoprcydrvcecisjtmzqgwbideneydnbuoihptjhlikyjzvaqnxhjyealclioppptkvauibfzywonegamaeyzvoactgmcxtwrtbebwpszuyhbwvjmpnefbckeoshtrjqdghpengycesjofdmcbaoxjbqlkjbwqeutmlfzglvgkxzcuedwsrdkdyjsjjyfrkucyzqbfrouumoojgzwpaskqtvrvaaplhatnwievxmpoaljzuxvadggvuqpxpgghzbjajaxfuaosxrriussgovbrqdmvkvaqzfkvfwtypeeitsmwqzhwgzdnzuvwvtskciwunqomitpujpyvnjj";

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(createPollRequest)
                .when()
                .post("/polls")
                .then()
                .statusCode(400);
    }
}
