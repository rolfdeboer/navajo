env.client.username="";
env.client.password="";
env.client.server="http://hera1.dexels.com/sportlink/knvb/servlet/Postman";

//create init update message:
var initupdate = env.callService("club/InitUpdateClub");
env.popNavajo();

//create init message:

var initsearch = env.callService("club/InitSearchClubs");
	env.setValue('ClubSearch/ClubName','bern');
	env.log(env.getPropertyElement());
env.popNavajo();

var searchresult = env.callService("club/ProcessSearchClubs", initsearch);

var list = searchresult.getMessage('Club').getAllMessages();
var count = 0;
var listarray = new Array();
for ( var msg2 in Iterator(list)) {
	listarray[count] = msg2;
	count++;
}

var outputlist = env.addArrayMessage(output,'Names');
for ( var index in listarray) {
	var t =listarray[index].getProperty('ClubIdentifier').getValue();
	env.setValue(initupdate,'Club/ClubIdentifier',t);
	var pp = env.callService("club/ProcessQueryClub",initupdate);
	var element = env.addElement(outputlist);
	env.addProperty(element,"ClubName",pp.getProperty("ClubData/ClubName"));
}

env.log('End of scirpt');