package br.com.leonardoferreira.poc.config

import br.com.leonardoferreira.poc.channel.CreatedContactChannel
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(CreatedContactChannel::class)
class StreamConfig
