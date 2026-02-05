package com.example.cmplistview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import cmplistview.composeapp.generated.resources.Res
import cmplistview.composeapp.generated.resources.compose_multiplatform
import cmplistview.composeapp.generated.resources.date_range_24px

data class ListItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val dateTime: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val items = remember {
        listOf(
            ListItem(1, "Design Meeting", "Discuss new UI components", "Oct 24, 10:00 AM", Color(0xFF6200EE)),
            ListItem(2, "Coffee Break", "Relax and recharge", "Oct 24, 11:30 AM", Color(0xFF03DAC6)),
            ListItem(3, "Code Review", "Review pull requests", "Oct 24, 02:00 PM", Color(0xFFFF0266)),
            ListItem(4, "Team Lunch", "Try the new Italian place", "Oct 24, 01:00 PM", Color(0xFF3700B3)),
            ListItem(5, "Gym Session", "Leg day today!", "Oct 24, 06:00 PM", Color(0xFF018786)),
            ListItem(6, "Project Launch", "Final checks before live", "Oct 25, 09:00 AM", Color(0xFFFFDE03))
        )
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { 
                            Text(
                                "My Schedule",
                                fontWeight = FontWeight.Bold
                            ) 
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items) { item ->
                        ItemRow(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(item: ListItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(item.color.copy(alpha = 0.1f), Color.White)
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(item.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = Color.Black
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(Res.drawable.date_range_24px),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = item.color
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.dateTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = item.color,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(item.color)
            )
        }
    }
}
