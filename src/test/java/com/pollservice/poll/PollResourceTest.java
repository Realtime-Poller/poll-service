package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class PollResourceTest {
    private Long id;
    private Instant createdTimestamp;
    private String title;
    private String description;

    @Transactional
    @BeforeEach
    public void setup() {
        Poll poll = new Poll();
        poll.setTitle("test poll title");
        poll.setDescription("test poll description");
        poll.persist();

        id = poll.id;
        title = poll.getTitle();
        description = poll.getDescription();
        createdTimestamp = poll.getCreatedTimestamp();
    }

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

    @Test
    public void testGetPoll() {
        // Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/polls/{id}", id)
                        .then()
                        .statusCode(200)
                        .extract().as(PollResponse.class);

        assertEquals("test poll title", pollResponse.title);
        assertEquals("test poll description", pollResponse.description);
        assertEquals(createdTimestamp.truncatedTo(ChronoUnit.MILLIS), pollResponse.createdTimestamp.truncatedTo(ChronoUnit.MILLIS));
        assertEquals(id, pollResponse.id);
    }

    @Test
    public void testGetPoll_NonExistingId() {
        //Assert
        Long nonExistingId = id + 100;
        //Act & Assert
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/polls/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdatePoll_TitleAndDescription() {
        //Arrange
        String newTitle = "new test poll title";
        String newDescription = "new test poll description";
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = newTitle;
        updatePollRequest.description = newDescription;

        //Act & Assert
        PollResponse pollResponse =
                given()
                .contentType(ContentType.JSON)
                .body(updatePollRequest)
                .when()
                .patch("/polls/{id}", id)
                .then()
                .statusCode(200)
                .extract().as(PollResponse.class);

        assertEquals(id, pollResponse.id);
        assertEquals(createdTimestamp.truncatedTo(ChronoUnit.MILLIS), pollResponse.createdTimestamp.truncatedTo(ChronoUnit.MILLIS));
        assertEquals(newTitle, pollResponse.title);
        assertEquals(newDescription, pollResponse.description);
        assertNotNull(pollResponse.lastUpdatedTimestamp);
    }

    @Test
    public void testUpdatePoll_OnlyTitle() {
        //Arrange
        String newTitle = "new test poll title";
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = newTitle;
        updatePollRequest.description = null;

        //Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(updatePollRequest)
                        .when()
                        .patch("/polls/{id}", id)
                        .then()
                        .statusCode(200)
                        .extract().as(PollResponse.class);

        assertEquals(id, pollResponse.id);
        assertEquals(createdTimestamp.truncatedTo(ChronoUnit.MILLIS), pollResponse.createdTimestamp.truncatedTo(ChronoUnit.MILLIS));
        assertEquals(newTitle, pollResponse.title);
        assertEquals(description, pollResponse.description);
        assertNotNull(pollResponse.lastUpdatedTimestamp);
    }

    @Test
    public void testUpdatePoll_OnlyDescription() {
        //Arrange
        String newDescription = "new test poll description";
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = null;
        updatePollRequest.description = newDescription;

        //Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(updatePollRequest)
                        .when()
                        .patch("/polls/{id}", id)
                        .then()
                        .statusCode(200)
                        .extract().as(PollResponse.class);

        assertEquals(id, pollResponse.id);
        assertEquals(createdTimestamp.truncatedTo(ChronoUnit.MILLIS), pollResponse.createdTimestamp.truncatedTo(ChronoUnit.MILLIS));
        assertEquals(title, pollResponse.title);
        assertEquals(newDescription, pollResponse.description);
        assertNotNull(pollResponse.lastUpdatedTimestamp);
    }

    @Test
    public void testUpdatePoll_NonExistentId() {
        //Arrange
        String newTitle = "new test poll title";
        String newDescription = "new test poll description";
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = newTitle;
        updatePollRequest.description = newDescription;

        Long nonExistingId = id + 100;

        //Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(updatePollRequest)
                .when()
                .patch("/polls/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdatePoll_TitleTooLong() {
        //Arrange
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = "fofcnsqnfkkprtokxzfimatehnvaylpykizxxnzorihjmzwmfwgemgogcueoizhdqlthjgwbzxbwjmwmhgdzdznwwsywwnpktokfejkjkmqvnrjczljliuowfpkzpguzcnebyldyhfetvnhbmyooiivcihyhzfdqxodemxqnorbqukykgigedgmbykfbozzztdhyoqsao";
        updatePollRequest.description = "test poll description";

        //Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(updatePollRequest)
                .when()
                .patch("/polls/{id}", id)
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdatePoll_DescriptionTooLong() {
        //Arrange
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = "test poll title";
        updatePollRequest.description = "fgffoldvcosxewlbaprzukliheymvfkhafjaonlgnyuajkztnnxbknpzqdktyjydnzabsiihljzrqnsxacgfmmeoveujlxhycxomzjefvnjgphmdslrwuppgeiyxmscotrbrokvxcnibtnkbtepqjhojolmfvvagmmkvypbjckeubslultzsinomuirpoldjjyijomxbdikvbiaycodfluzhaggfuegfsnotmjntlonnawjktjushtmihcdpavfcijnudzdckxkfeumxducosrvfypnqvdwlijbffolamnqlhhqqmmgygazrtjwwctebbgznuotnzbtxzhyffcyotclviehfyimlfzigfdldnqzrrscelyduzcstnhviawmaaspekkjngyileizqtajbrailtsneyjoduzxvxwmgmphedpeopoaakqtomwfstviholyczkexshxzmemjuixxitmazxtdsreinkyxafiochjnqciofspxvmgoqwcahjszidpzcovswhrxrvjkhfrovgkytvkkyshdsdkxyinywlihvrfvdvnpcavbtqhbdataooirfvrhdxflxhjxzodjmvqhiufvdbuodeaimkprkvtgavpszvatarvvfertrtgcmktdbxacmautiudyekeotkojifbdibzvjanxlyaviacuxghginoryzhogybjebykqigfipboxmhjeyoxkenugbtspudhyxczfkqbjoqhbghhcuhklunpbkrbdnrdqqmolxtapwnyicywtmydlfubuepcgedxeqosnjiqnkhrbskxywatjvmxbrpfkuojbxcssvybthvadxcqdtsduqyifnnsnyirqxcxmvbluqvolvnqbfvfbnvrnuiyugkvqgpfqmikbgpmbjofmuhbvrzfdbmbxfrztfxiambjnuqcalneytkdgvtqmawwlpbsztnfyiiertitvzabgbnbqvdfxvmcuvpmadeilqjkqvjpmlribfycjsmlzrvtbytbidwoxhtvafsnznakorfawudgscjrxhofyufpfagncshzjxwjafkislqmkavyzfxukqeuefohbnofhweacfoiuykewhaoqbxkimgsqkodyxkaztiooadsezgzzwezilvwcrybufvxdelhlzkfxfnragegyhuxjuxamwexhnagbwvugqlmxfsbrflvgajssmdqygljovdhpnccdphfdsxgpyekxbpdfvmnvnobawdwlzscyaqvrlserurqcyjmckpqgcnnqzbynsptuocfwuswhnhrrtffbbspbzlznafsylgxvhzaujeqehwxbihaepwjdewwqurunazzfwsgzinpngcwycjelezxupysbqpdwdvtlmpdpbktbvujnpbjmrcyoswlvvyovpwdpfpfcccukhcckjwolfqtrcxsvuqjgbidskeghzevemazrskdtwgwnrgijxbnlffmsyscsjoqovxqapkgeqsmndxpmrcgbcsgcdfxfubmprphsaxibdcjtztqlpmgpmbzwgrxecwaypyeizuguwiusqessrmtcaotczirtkmbaeoosolsfptythnyncafxpeqlwxuqaajrfdariaursirvpqngtwsgkcoolamuyotywsjlshebsxyginmhogznkckyruvirnexfwgkfsnpghdfcwljqyfijziamkvkjjzhjflfpnsfxwjgbwkmompphnuvllqhodztoajrqqwhvhvfwmqatzzgulncvkjwfxgdsiqrkettclytnltmnnwzehnnwkdhqmflclzxbkhlslbbdftywyhvvanhjkjczorqiposmtraaibxoqyqtaaqvqkkcsqmmndqwuydyvvnwwqqtrroyauksqxvrsjyjcnbkcbupszhygvjbwbayzfbnsujcwxlxztcnhrdgldbgpocnbbsnryzdnzxgmgebfuseiyvstoughyliqulrvlprbztjrzusfhevkarfziholgklkczfpshvvcpydurynjkamistequfxayzrfukrgpbfwqazvammfkkwncddgojexglgdgdocgsopdjdoqievnaocrjurbqyuafjnrjyvrntocxoxaabpqbnujxluyifwiiqsrxiibjpagustjxripyurpjifakkjyvqikzvecoxbtwgcfxgtmzlywmhxizxvkrcntqkzeukpvouzhjnbgbecsrzywiyqisvvpqojteibqfnztzvouaczsvvmtugvcesorcnfytjbgkguldoxewbtxiympgynlynphftezgawdbjomftcizurbpsxujkpxvfgqcqluegdmnngzktkogwrcshafrqnpisocwptlyxqwqbfzexoqjjhndidtszvpraefenbqpccaawtcijtjurgorcmkdrrqibjtweevngcookfbikcdiaobmlzkdorefqltqnlemavnnewlhxpyjoloteyowqxaskxhkksgfqsolanrdgifumwwgabjnxrxdurqegbwtvrqbafqmxfeydskckpefvxzjufckvqfkqwsppmhepfqdxvmhqjhobpiejbjcvxxdwxqjiarltymdpltypjotcrewyjefyresqmfvmtrynhwvsjmiejifbtfmmceoarzwletqhfbytavnpojgnajjjvssfvyrxhzzfyljcrkxufjcphjyuktntudkpebkhvciswpjhumfzcvtbhnucfnocwuszgwcedqhqbruymkorzxocqckvgbkopprwyabervvikghincvoxcbhchgywbrmutjlhimqsxjhhjlfllrvzdsgoqxwgydmzinfhvvfqeyrfdupabhnezbzyuduviewvzlyrkshqvqyuqdofpylebxhbrvdjhqzrkqgudgfecahglrvijrmhjfaoblwugebajvaijajmuzwbrpiqzumpczpfoxogwlbnrbxbxhtgzjsarheubrwprlkaspnrctotiykjywwutqbzumrovgxrqpzmwktnaldmwlfovkpxgxmsowmgdoqxrcplhvpexpntngowibyrkjmubepsjbfwcqjpkamcttgstrnjawnsizrdmlrdqrmuxohlcauawnyisqqzzayzlmigmhyjrgxsiafxfsvlxnbfczjtzicyfxnzakqbefwrpprjgufwqkfprnezjjnyqgztagcpbflsvxiijwnmfvvfkefpqqpewjghmwwiuyboskwnxetfnszjkbeifrhwpaumkjequyvhairyaiitxjxaflvsffkdwaclogkufwalypkpywkusmksxfsvdlvdyqtcpoaekrwyuwbcxnxydkiwvslwjpbwcuuzoiquungcvvjagnaslcwdujsfxdbeiwofadupqwzznoeprjwuhmsjkdctjxgpnebxzptoqqnzbjrwylagxyhjzpffbwzgwhdscjsclbbqqgnevkeqofxiqwplcepjchkeaqerzhxsyvcmtddywdljhhokhpgqsncuvdqobviklckipyavtsbtuszrquhkmmbdzlgjyhpemzughainodjfvxchyvgtkdyaqeajokkzgwhwgmdkggoiuskxvxjwhsfsdaavgwkuhrizvdtylptrbsxqpbjjjwerjxepcaciymtxtcumvqdbhvefpvlzxtrioqxzebzfrghgskheomvfgduosoyrcjzjbrhquesqyblbnbnjcvfowpfiwwhscjqnjzoytivcgulhpmxzvaldqhitbxbpcxcqbytusbubqucswsiokxankojqyhygtghdspuquslqqmjknzsumnynxxtsqtnabfcmcfeiqsjatlavquxqstcbdxznewrgmkmvnylkuubizqrksbmssbctvxuyiupbsgjflzlxeotmomuxlensomxunmwcdgvxzivurgtcmtunwqxyjzttnjpdqmbhdfgsnylingvymtylxnxldmlfsafrwakeubpdcmvfkidykrugrpjuuecmuiyovszjfuiiobstnnzgufmsgmmhxzvtoxychwhkbnjkmwkdxpkvhenczvssnowgzrtzuxwjulotvssbjkvfskgsntsvprfwakwpruruopbvgrkweofgscdilsaietzaaagjmrqskrxtjvytwbyrsphxbhdramcwwftrskvpfehjycwcgmkbbgyuvzgxyfpqlitnylkurhxvybcbyftwpwnrwgapobplivjwssoaegcldgftxjxhboejxyeveuajoxapiztkrvckajxdewpzhmvfgeuzhskjdgiwmlriymcmcixklqtuotcirmztkbqpgaretxtmoqzaesevahxeknfeqfhibojklrpgtrwomsadcpurtladktxvdmvorxqfftftjhubkkeipxqsgjnyvkacibcnnnppveeqnhzenhcchcciybakzdpuzqexnqqnrmljhpzrlfimjxyuragckfddrkgoymqdlpzwmwuonwpesubyurlxxtxfvhkqqavvfevgxbrxghxqftqztjkoqwopiftkvmjeabdffeitpydekihpfmparasqshumabwxfinytrqnconindlrntjjsfurdqeguindsrofaclpsisdexotnvxiekbyqotxilpeukeoprcydrvcecisjtmzqgwbideneydnbuoihptjhlikyjzvaqnxhjyealclioppptkvauibfzywonegamaeyzvoactgmcxtwrtbebwpszuyhbwvjmpnefbckeoshtrjqdghpengycesjofdmcbaoxjbqlkjbwqeutmlfzglvgkxzcuedwsrdkdyjsjjyfrkucyzqbfrouumoojgzwpaskqtvrvaaplhatnwievxmpoaljzuxvadggvuqpxpgghzbjajaxfuaosxrriussgovbrqdmvkvaqzfkvfwtypeeitsmwqzhwgzdnzuvwvtskciwunqomitpujpyvnj";

        //Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(updatePollRequest)
                .when()
                .patch("/polls/{id}", id)
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdatePoll_TitleAndDescriptionNull() {
        //Arrange
        UpdatePollRequest updatePollRequest = new UpdatePollRequest();
        updatePollRequest.title = null;
        updatePollRequest.description = null;

        //Act & Assert
        PollResponse pollResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(updatePollRequest)
                        .when()
                        .patch("/polls/{id}", id)
                        .then()
                        .statusCode(200)
                        .extract().as(PollResponse.class);

        assertEquals(id, pollResponse.id);
        assertEquals(createdTimestamp.truncatedTo(ChronoUnit.MILLIS), pollResponse.createdTimestamp.truncatedTo(ChronoUnit.MILLIS));
        assertEquals(title, pollResponse.title);
        assertEquals(description, pollResponse.description);
        assertNull(pollResponse.lastUpdatedTimestamp);
    }

    @Test
    public void testDeletePoll_HappyPath() {
        //Act & Assert
        given()
                .when()
                .delete("/polls/{id}", id)
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeletePoll_NoSuchId() {
        Long nonExistingId = id + 100;
        given()
                .when()
                .delete("/polls/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }
}
