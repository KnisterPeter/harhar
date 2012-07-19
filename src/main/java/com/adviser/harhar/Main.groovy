package com.adviser.harhar

import org.codehaus.jackson.map.ObjectMapper

import com.adviser.harhar.model.Har


def runHar = { options, file ->
  int users = options.users ? options.users as int : 1
  int repeat = options.repeat ? options.repeat as int : 1
  def url = options.url
  def sim = new Simulator(users, repeat, url)
  sim.run(new ObjectMapper().readValue(new File(file), Har.class))
  sim.result.print()
}

CliBuilder cli = new CliBuilder(usage:'harhar [options] <har-file>')
cli.h(longOpt:'help', 'This message')
cli.u(longOpt:'users', args:1, 'The number of concurrent users')
cli.r(longOpt:'repeat', args:1, 'The number of repititions per user')
cli._(longOpt:'url', args:1, 'The base url to execute the har against')
cli._(longOpt:'har-file', 'HAR file to read in')
OptionAccessor options = cli.parse(args)
if (options.help) {
  cli.usage()
} else {
  runHar(options, options.arguments().first())
}
