def eliza;

if ('eliza' in memcache) {
	eliza = memcache['eliza']
} else {
	eliza = new Eliza.ElizaMain()
	eliza.readScript(false, "http://chayden.org/eliza/script")
	memcache['eliza'] = eliza
}

def elizaResponse = (params.input != null) ? eliza.processInput(params.input) : eliza.processInput("Hello")

request.setAttribute 'eliza_response', elizaResponse

forward 'eliza.gtpl'

