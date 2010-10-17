
//def message = [from:"matt.stine@gmail.com", body:"test"]

def s = "Received message from ${message.from} with body ${message.body}"

log.info s

def eliza;

if ('eliza' in memcache) {
	eliza = memcache['eliza']
} else {
	eliza = new Eliza.ElizaMain()
	eliza.readScript(false, "http://chayden.org/eliza/script")
	memcache['eliza'] = eliza
}

def elizaInput = message.body
def elizaResponse = (elizaInput != null) ? eliza.processInput(elizaInput) : eliza.processInput("Hello")
log.info elizaResponse

def recipient = message.from

if (xmpp.getPresence(recipient).isAvailable()) {
	def status = xmpp.send(to: recipient, body: elizaResponse)
}
