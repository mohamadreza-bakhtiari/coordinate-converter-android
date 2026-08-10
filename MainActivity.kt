/*
 * Copyright (c) 2026 Mohamadreza Bakhtiari
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package com.bkt.coordinateconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bkt.coordinateconverter.ui.theme.CoordinateConverterTheme


class GeoPoint {
    var lat: Double = 0.0
    var lon: Double = 0.0
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CoordinateConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CoordinateConverterLayout()
                }
            }
        }
    }
}

@Composable
fun CoordinateConverterLayout() {
    var latDegAmountInput by remember { mutableStateOf("") }
    val latDegAmount = latDegAmountInput.toDoubleOrNull() ?: 0.0

    var latMinAmountInput by remember { mutableStateOf("") }
    val latMinAmount = latMinAmountInput.toDoubleOrNull() ?: 0.0

    var latSecAmountInput by remember { mutableStateOf("") }
    val latSecAmount = latSecAmountInput.toDoubleOrNull() ?: 0.0

    var lonDegAmountInput by remember { mutableStateOf("") }
    val lonDegAmount = lonDegAmountInput.toDoubleOrNull() ?: 0.0

    var lonMinAmountInput by remember { mutableStateOf("") }
    val lonMinAmount = lonMinAmountInput.toDoubleOrNull() ?: 0.0

    var lonSecAmountInput by remember { mutableStateOf("") }
    val lonSecAmount = lonSecAmountInput.toDoubleOrNull() ?: 0.0

    val dd: GeoPoint = convertHMStoDD(latDegAmount, latMinAmount, latSecAmount,
        lonDegAmount, lonMinAmount, lonSecAmount)

    val ddLat: Double = dd.lat
    val ddLon: Double = dd.lon

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 30.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.input_coordinates),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.latitude),
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Row {
            EditNumberField(value = latDegAmountInput,
                onValueChange = { latDegAmountInput = it },
                label = stringResource(R.string.degrees),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .width(85.dp)
            )

            EditNumberField(value = latMinAmountInput,
                onValueChange = { latMinAmountInput = it },
                label = stringResource(R.string.minutes),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .width(85.dp)
            )

            EditNumberField(value = latSecAmountInput,
                onValueChange = { latSecAmountInput = it },
                label = stringResource(R.string.seconds),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .width(85.dp)
            )
        }

        Text(
            text = stringResource(R.string.longitude),
            modifier = Modifier.align(alignment = Alignment.Start)
        )

        Row {
            EditNumberField(value = lonDegAmountInput,
                onValueChange = { lonDegAmountInput = it },
                label = stringResource(R.string.degrees),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .width(85.dp)
            )

            EditNumberField(value = lonMinAmountInput,
                onValueChange = { lonMinAmountInput = it },
                label = stringResource(R.string.minutes),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .width(85.dp)
            )

            EditNumberField(value = lonSecAmountInput,
                onValueChange = { lonSecAmountInput = it },
                label = stringResource(R.string.seconds),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .width(85.dp)
            )
        }

        Text(
            text = stringResource(R.string.latitude_in_dd,
                "%.6f".format(ddLat)),
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Text(
            text = stringResource(R.string.longitude_in_dd,
                "%.6f".format(ddLon)),
            modifier = Modifier.align(alignment = Alignment.Start)
        )

        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(value: String,
                    onValueChange: (String) -> Unit,
                    label: String,
                    modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

private fun convertHMStoDD(latD: Double, latM: Double, latS: Double,
                           lonD: Double, lonM: Double, lonS: Double): GeoPoint
{
    val latDD = latD + (latM / 60) + (latS / 3600)
    val lonDD = lonD + (lonM / 60) + (lonS / 3600)

    val geoPoint = GeoPoint()
    geoPoint.lat = latDD
    geoPoint.lon = lonDD

    return geoPoint
}

@Preview(showBackground = true)
@Composable
fun CoordinateConverterLayoutPreview() {
    CoordinateConverterTheme {
        CoordinateConverterLayout()
    }
}